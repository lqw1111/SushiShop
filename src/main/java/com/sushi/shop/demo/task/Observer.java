package com.sushi.shop.demo.task;

public interface Observer {

    void update(TaskStatus status, Object task) throws Exception;
}
