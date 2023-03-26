package com.macro.service.impl;

import java.util.ArrayList;
import java.util.List;

public class IdTest {

    public static void main(String[] args) throws InterruptedException {
        CustomIdGenerator customIdGenerator=new CustomIdGenerator();
        List<Thread> threadList=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadList.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(customIdGenerator.generateDistributeId("test"));
                }
            }));
        }
        threadList.forEach(c->c.start());
        Thread.sleep(1000);
        System.out.println(customIdGenerator.generateDistributeId("test"));
    }
}
