package com.sushi.shop.demo.task;

import lombok.Data;

@Data
public class SushiThread extends Thread {
    private SushiTask task;

    public SushiThread(SushiTask task) {
        super(task);
        this.task = task;
    }
}
