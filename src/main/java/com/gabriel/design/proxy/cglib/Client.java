package com.gabriel.design.proxy.cglib;

import com.gabriel.service.Tank;
import java.lang.reflect.Method;
import java.util.Random;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2021.05.15 15:49
 */
public class Client {

  public static void main(String[] args) {

    // 代理类class文件存入本地磁盘方便我们反编译查看源码
    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "G:\\Code\\github");
    //1. 创建一个工具类
    Enhancer enhancer = new Enhancer();
    //2. 设置父类
    enhancer.setSuperclass(Source.class);
    //3. 设置回调函数
    enhancer.setCallback(new LogMethodInterceptor());
    //4. 创建子类对象，即代理对象
    Source source = (Source)enhancer.create();

    source.method();
  }
}


class LogMethodInterceptor implements MethodInterceptor {

  private void before() {
    System.out.println("执行方法之前的日志");
  }

  private void after() {
    System.out.println("执行方法之后的日志");
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

    System.out.println("生成的代理类："+o.getClass().getName());

    System.out.println("被代理类继承的父类："+o.getClass().getSuperclass().getName());

    before();
    Object result = null;
    result = methodProxy.invokeSuper(o, objects);
    after();
    return result;
  }
}


class Source{
  public void method() {
    System.out.println("程序员找bug调试:palapalapala(键盘声)");
    try {
      Thread.sleep(new Random().nextInt(10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}