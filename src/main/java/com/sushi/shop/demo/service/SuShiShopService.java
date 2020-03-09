package com.sushi.shop.demo.service;

import com.sushi.shop.demo.dto.ResponseDTO;
import com.sushi.shop.demo.entity.Sushi;
import com.sushi.shop.demo.entity.SushiOrder;
import com.sushi.shop.demo.mapper.SushiMapper;
import com.sushi.shop.demo.mapper.SushiOrderMapper;
import com.sushi.shop.demo.model.SushiOrderStatus;
import com.sushi.shop.demo.task.SushiTask;
import com.sushi.shop.demo.task.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuShiShopService {

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private SushiMapper sushiMapper;

    @Autowired
    private SushiOrderMapper sushiOrderMapper;

    public ResponseDTO orders(String sushiName) throws Exception {

        Sushi sushi = sushiMapper.findByName(sushiName);

        SushiOrder order = new SushiOrder();
        order.setSushiId(sushi.getId());
        order.setStatusId(SushiOrderStatus.created.getType());
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        sushiOrderMapper.insert(order);

        order = sushiOrderMapper.findSushiOrder(order);

        SushiTask task = new SushiTask(order, sushi.getTimeToMake());
        task.attach(taskManager);
        task.init();

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(0);
        responseDTO.setMsg("Order submitted");
        responseDTO.setBody(order);
        return responseDTO;
    }

    public ResponseDTO cancel(Integer orderId) throws Exception {
        SushiTask task = taskManager.getTaskFromId(orderId);
        if (task != null) {
            task.cancel();

            SushiOrder order = sushiOrderMapper.findById(orderId);
            order.setStatusId(SushiOrderStatus.cancel.getType());
            sushiOrderMapper.updateStatus(order);
        } else {
            throw new Exception("Task Not Exist");
        }

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(0);
        responseDTO.setMsg("Order cancelled");
        return responseDTO;
    }

    public ResponseDTO pause(Integer orderId) throws Exception {
        SushiTask task = taskManager.getTaskFromId(orderId);
        if (task != null) {
            task.pause();

            SushiOrder order = sushiOrderMapper.findById(orderId);
            order.setStatusId(SushiOrderStatus.paused.getType());
            sushiOrderMapper.updateStatus(order);
        } else {
            throw new Exception("Task Not Exist");
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(0);
        responseDTO.setMsg("Order paused");
        return responseDTO;
    }

    public ResponseDTO resume(Integer orderId) throws Exception {
        SushiTask task = taskManager.getTaskFromId(orderId);
        if (task != null) {
            task.resume();

            SushiOrder order = sushiOrderMapper.findById(orderId);
            order.setStatusId(SushiOrderStatus.inProgress.getType());
            sushiOrderMapper.updateStatus(order);

        } else {
            throw new Exception("Task Not Exist");
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg("Order resumed");
        responseDTO.setCode(0);
        return responseDTO;
    }

    public ResponseDTO status() {
        List<SushiOrder> sushiOrderList = sushiOrderMapper.findAll();
        Map<String, List<SushiOrder>> res = new HashMap<>();

        for (SushiOrder order : sushiOrderList) {
            List<SushiOrder> list = res.getOrDefault(mapStatusName(order.getStatusId()), new ArrayList<>());
            list.add(order);
            res.put(mapStatusName(order.getStatusId()), list);
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(0);
        responseDTO.setBody(res);
        return responseDTO;
    }

    private String mapStatusName(int status) {
        if (status == SushiOrderStatus.created.getType()) {
            return "pending";
        } else if (status == SushiOrderStatus.inProgress.getType()) {
            return "in-progress";
        } else if (status == SushiOrderStatus.paused.getType()) {
            return "paused";
        } else if (status == SushiOrderStatus.finish.getType()) {
            return "finished";
        } else if (status == SushiOrderStatus.cancel.getType()) {
            return "cancel";
        } else {
            return "";
        }
    }


}
