package com.gabriel.design.proxy.v01;

public class Test {

  public static void main(String[] args) {

//    //创建代理对象
//    Sourceable source = new Proxy();
//
//    //通过代理对象，调用被代理对象的方法。
//    source.method();

    Pig pig = new Pig();
    Animal fatPigProxy =new FatPigProxy(pig);
    Animal pigletProxy =new PigletProxy(fatPigProxy);
    pigletProxy.bark();

  }

}
