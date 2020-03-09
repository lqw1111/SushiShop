package com.sushi.shop.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SushiOrder {
    private Integer id;
    private Integer statusId;
    private Integer sushiId;
    private Timestamp createdAt;
}
