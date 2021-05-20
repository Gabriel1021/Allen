package com.gabriel.design.proxy.v02;

import com.gabriel.service.Tank;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author : zouxiaoxiang
 * @description : JDK动态代理
 * @date : 2021.05.14 15:39
 */
public class Source implements SourceAble{

  @Override
  public void method() {
    System.out.println("程序员找bug调试:palapalapala(键盘声)");
    try {
      Thread.sleep(new Random().nextInt(10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

    //创建目标对象
    SourceAble sourceAble = new Source();
    // 修改系统属性，把内存中的文件打印出来
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
    //给目标对象，创建代理对象, 可以转成 SourceAble
    SourceAble s = (SourceAble)Proxy.newProxyInstance(Source.class.getClassLoader(),
        new Class[]{SourceAble.class},
        new LogHandler(sourceAble));

    // 返回代理对象
    System.out.println("s.getName:" +s.getClass().getName());
    // 调用代理对象的方法调用目标对象方法
    s.method();
  }

}


class LogHandler implements InvocationHandler {

  SourceAble sourceAble;

  public LogHandler(SourceAble sourceAble) {
    this.sourceAble = sourceAble;
  }


  private void before(String methodName) {
    System.out.println("JDK代理~~"+"method " + methodName + " start..");
  }

  private void after(String methodName) {
    System.out.println("JDK代理~~"+"method " + methodName + " end!");
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    before(method.getName());
    Object o = method.invoke(sourceAble, args);
    after(method.getName());
    return o;
  }
}

interface SourceAble {

  void method();

}