package com.sushi.shop.demo.controller;

import com.sushi.shop.demo.dto.ResponseDTO;
import com.sushi.shop.demo.service.SuShiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
public class SushiShopController {

    @Autowired
    private SuShiShopService suShiShopService;

    @RequestMapping(value = "/orders", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO orders(@RequestBody LinkedHashMap name) throws Exception {
        return suShiShopService.orders((String) name.get("sushi_name"));
    }

    @RequestMapping(value = "/orders/cancel/{order_id}", method = RequestMethod.PUT)
    public ResponseDTO cancel(@PathVariable("order_id") Integer orderId) throws Exception {
        return suShiShopService.cancel(orderId);
    }

    @RequestMapping(value = "/orders/pause/{order_id}", method = RequestMethod.PUT)
    public ResponseDTO pause(@PathVariable("order_id") Integer orderId) throws Exception {
        return suShiShopService.pause(orderId);
    }

    @RequestMapping(value = "/orders/resume/{order_id}", method = RequestMethod.PUT)
    public ResponseDTO resume(@PathVariable("order_id") Integer orderId) throws Exception {
        return suShiShopService.resume(orderId);
    }

    @RequestMapping(value = "/orders/status", method = RequestMethod.GET)
    public ResponseDTO status(){
        return suShiShopService.status();
    }

}
