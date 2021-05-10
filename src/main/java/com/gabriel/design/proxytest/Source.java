package com.gabriel.design.proxytest;

/**
 * @author Administrator
 */
public class Source implements Sourceable {

  @Override
  public void method() {
    System.out.println("执行被代理类的方法...");
  }

}
