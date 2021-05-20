package com.gabriel.design.proxy.spring.v2;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TimeProxy {

    @Before("execution (void com.gabriel.design.proxy.spring.v2.Source.method())")
    public void before() {
        System.out.println("v2 method start.." + System.currentTimeMillis());
    }

    @After("execution (void com.gabriel.design.proxy.spring.v2.Source.method())")
    public void after() {
        System.out.println("v2 method stop.." + System.currentTimeMillis());
    }

}
