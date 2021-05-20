package com.gabriel.design.proxy.spring.v2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring aop test
 */

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app_auto.xml");
        Source t = (Source)context.getBean("source");
        System.out.println(t.getClass().getName());
        t.method();
    }
}
