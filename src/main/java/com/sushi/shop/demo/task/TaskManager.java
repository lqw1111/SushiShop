package com.sushi.shop.demo.task;

import com.sushi.shop.demo.entity.SushiOrder;
import com.sushi.shop.demo.mapper.SushiOrderMapper;
import com.sushi.shop.demo.model.SushiOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TaskManager implements Observer {

    @Autowired
    private SushiOrderMapper sushiOrderMapper;

    private static final int MAX_PARALLEL_TASK = 3;

    ConcurrentLinkedDeque<SushiThread> waiting = new ConcurrentLinkedDeque<>();
    ConcurrentHashMap<Integer, SushiThread> making = new ConcurrentHashMap<>();
    ConcurrentHashMap<Integer, SushiThread> pausing = new ConcurrentHashMap<>();


    public void onCreated(SushiTask task) {
        SushiThread thread = new SushiThread(task);
        if (making.size() < MAX_PARALLEL_TASK) {
            making.put(thread.getTask().getId(), thread);

            SushiOrder order = task.getSushiOrder();
            order.setStatusId(SushiOrderStatus.inProgress.getType());
            sushiOrderMapper.updateStatus(order);

            thread.start();
        } else {
            waiting.addLast(thread);
        }
    }

    private void onPaused(SushiTask task) {
        SushiThread target = making.remove(task.getId());
        pausing.put(task.getId(), target);

        if (waiting.size() > 0) {
            SushiThread newThread = waiting.removeFirst();
            making.put(newThread.getTask().getId(), newThread);
            newThread.start();
        }
    }

    private void onResume(SushiTask task) {
        SushiThread target = pausing.get(task.getId());

        if (making.size() < MAX_PARALLEL_TASK) {
            making.put(task.getId(), target);
        } else {
            waiting.addFirst(target);
        }
    }

    private void onFinished(SushiTask task) {
        making.remove(task.getId());

        SushiOrder order = task.getSushiOrder();
        order.setStatusId(SushiOrderStatus.finish.getType());
        sushiOrderMapper.updateStatus(order);

        getNewTaskExecute();
    }

    private void onCancel(SushiTask task) throws Exception {
        int id = task.getId();
        if (making.containsKey(id)) {
            making.get(id).interrupt();
            making.remove(id);
        } else if (pausing.containsKey(id)) {
            pausing.get(id).interrupt();
            pausing.remove(id);
        } else {
            SushiThread target = null;
            for (SushiThread t : waiting) {
                if (t.getTask().getId() == id) {
                    t.interrupt();
                    target = t;
                }
            }
            if (target != null)
                waiting.remove(target);
            else
                throw new Exception("Task Not Exist");
        }
        getNewTaskExecute();
    }

    private void getNewTaskExecute() {
        if (waiting.size() > 0) {
            SushiThread thread = waiting.removeFirst();
            making.put(thread.getTask().getId(), thread);
            if (!thread.isAlive()) {
                thread.start();
            }
            else {
                synchronized (thread.getTask()) {
                    thread.getTask().notify();
                }
            }
        }
    }


    @Override
    public void update(TaskStatus status, Object object) throws Exception {
        if (object instanceof SushiTask) {
            SushiTask task = (SushiTask) object;
            switch (status) {
                case CREATED:
                    onCreated(task);
                    break;
                case PAUSED:
                    onPaused(task);
                    break;
                case RESUMED:
                    onResume(task);
                    break;
                case FINISHED:
                    onFinished(task);
                    break;
                case CANCELED:
                    onCancel(task);
                    break;
            }
        }
    }

    public SushiTask getTaskFromId(Integer taskId) {
        if (making.containsKey(taskId)) {
            return making.get(taskId).getTask();
        } else if (pausing.containsKey(taskId)) {
            return pausing.get(taskId).getTask();
        } else {
            for (SushiThread thread : waiting) {
                if (taskId == thread.getTask().getId()) {
                    return thread.getTask();
                }
            }
        }
        return null;
    }
}
