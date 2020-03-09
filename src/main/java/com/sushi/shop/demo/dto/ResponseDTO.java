package com.sushi.shop.demo.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private Integer code;
    private String msg;
    private Object body;
}
