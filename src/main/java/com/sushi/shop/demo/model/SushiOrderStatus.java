package com.sushi.shop.demo.model;

public enum SushiOrderStatus {
    created(1), inProgress(2), paused(3), finish(4), cancel(5);

    private Integer type;

    SushiOrderStatus(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
