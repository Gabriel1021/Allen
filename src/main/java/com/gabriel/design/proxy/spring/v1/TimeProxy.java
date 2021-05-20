package com.gabriel.design.proxy.spring.v1;

public class TimeProxy {

    public void before() {
        System.out.println("v1 method start.." + System.currentTimeMillis());
    }

    public void after() {
        System.out.println("v1 method stop.." + System.currentTimeMillis());
    }

}
