package com.sushi.shop.demo.task;

import com.sushi.shop.demo.entity.SushiOrder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SushiTask extends Subject implements Runnable {
    private int totalTime;
    private int currentTime = 0;
    private SushiOrder sushiOrder;
    private int id;
    private int flag;

    private final int CREATED = 1;
    private final int PAUSE = 2;
    private final int CANCEL = 3;
    private final int RESUME = 4;

    @SneakyThrows
    @Override
    public void run() {
        while(currentTime < totalTime) {
            switch (flag) {
                case CREATED:
                    // timing
                    log.info("sushi task:" + id + " is running ...");
                    Thread.sleep(1000);
                    currentTime++;
                    break;
                case PAUSE:
                    // pause
                    log.info("sushi task:" + id + " is paused ...");
                    updateOrderStatus(TaskStatus.PAUSED);
                    synchronized (this) {
                        this.wait();
                    }
                    break;
                case CANCEL:
                    // cancel
                    log.info("sushi task:" + id + " is cancelled ...");
                    updateOrderStatus(TaskStatus.CANCELED);
                    return;
                case RESUME:
                    log.info("sushi task:" + id + " is resumed ...");
                    updateOrderStatus(TaskStatus.RESUMED);
                    flag = CREATED;
                    break;
            }
        }
        log.info("sushi task:" + id + " is finished ...");
        updateOrderStatus(TaskStatus.FINISHED);
    }

    public SushiTask(SushiOrder order, int time){
        this.sushiOrder = order;
        this.id = order.getId();
        this.totalTime = time;
    }

    public void init() throws Exception {
        flag = CREATED;
        updateOrderStatus(TaskStatus.CREATED);
    }

    public void pause() {
        flag = PAUSE;
    }

    public void resume() {
        synchronized (this) {
            this.notify();
        }
        flag = RESUME;
    }

    public void cancel() {
        flag = CANCEL;
        synchronized (this) {
            this.notify();
        }
    }

    public void updateOrderStatus(TaskStatus status) throws Exception {
        this.sushiOrder.setStatusId(status.getValue());
        this.inform(status, this);
    }

}
