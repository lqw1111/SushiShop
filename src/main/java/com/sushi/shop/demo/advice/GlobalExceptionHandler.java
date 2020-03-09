package com.sushi.shop.demo.advice;

import com.sushi.shop.demo.dto.ResponseDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseDTO handleException(Exception e){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(1);
        responseDTO.setMsg(e.getMessage());
        return responseDTO;
    }
}
