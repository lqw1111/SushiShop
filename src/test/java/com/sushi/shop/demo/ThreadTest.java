package com.sushi.shop.demo;

import com.sushi.shop.demo.entity.SushiOrder;
import com.sushi.shop.demo.task.SushiTask;
import com.sushi.shop.demo.task.TaskManager;

public class ThreadTest {

    public static void main(String[] args) throws Exception {


        SushiOrder order1 = new SushiOrder();
        order1.setId(1);
        SushiOrder order2 = new SushiOrder();
        order2.setId(2);
        SushiOrder order3 = new SushiOrder();
        order3.setId(3);
        SushiOrder order4 = new SushiOrder();
        order4.setId(4);
        SushiOrder order5 = new SushiOrder();
        order5.setId(5);
        SushiOrder order6 = new SushiOrder();
        order6.setId(6);


        SushiTask sushiTask1 = new SushiTask(order1, 10);
        SushiTask sushiTask2 = new SushiTask(order2, 30);
        SushiTask sushiTask3 = new SushiTask(order3, 40);
        SushiTask sushiTask4 = new SushiTask(order4, 20);
        SushiTask sushiTask5 = new SushiTask(order5, 20);
        SushiTask sushiTask6 = new SushiTask(order6, 20);

        TaskManager manager = new TaskManager();
        sushiTask1.attach(manager);
        sushiTask2.attach(manager);
        sushiTask3.attach(manager);
        sushiTask4.attach(manager);
        sushiTask5.attach(manager);
        sushiTask6.attach(manager);

        sushiTask1.init();
        sushiTask2.init();
        sushiTask3.init();
        sushiTask4.init();
        sushiTask5.init();
        sushiTask6.init();


        System.out.println("==================");

        sushiTask1.cancel();
        Thread.sleep(2000);

        sushiTask2.cancel();
        Thread.sleep(2000);
        sushiTask3.cancel();


        Thread.sleep(2000);
        System.out.println("==================");

//        sushiTask2.resume();
        Thread.sleep(2000);


//        sushiTask3.resume();
    }

}
