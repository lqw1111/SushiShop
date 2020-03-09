package com.sushi.shop.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemoApplication.class, args);
    }

}
