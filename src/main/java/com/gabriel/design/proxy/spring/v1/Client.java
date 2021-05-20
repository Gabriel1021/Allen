package com.gabriel.design.proxy.spring.v1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author : zouxiaoxiang
 * @description : spring aop test
 * @date : 2021.05.14 15:39
 */
public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("app.xml");
        Source t = (Source)context.getBean("source");
        System.out.println(t.getClass().getName());
        t.method();
    }
}
