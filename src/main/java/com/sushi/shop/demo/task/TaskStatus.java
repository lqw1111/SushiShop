package com.sushi.shop.demo.task;

public enum TaskStatus {
    CREATED(1), PAUSED(2), RESUMED(3), FINISHED(4), CANCELED(5);

    private int value;

    TaskStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
