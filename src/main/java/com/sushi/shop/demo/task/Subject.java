package com.sushi.shop.demo.task;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    List<Observer> list = new ArrayList<>();

    public void attach(Observer observer) {
        list.add(observer);
    }

    public void inform(TaskStatus status, Object object) throws Exception {
        for (Observer observer : list) {
            observer.update(status, object);
        }
    }
}
