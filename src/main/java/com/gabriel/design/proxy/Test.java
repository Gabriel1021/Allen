package com.gabriel.design.proxy;

import com.gabriel.design.proxytest.Proxy;
import com.gabriel.design.proxytest.Sourceable;

public class Test {

  public static void main(String[] args) {

//    //创建代理对象
//    Sourceable source = new Proxy();
//
//    //通过代理对象，调用被代理对象的方法。
//    source.method();

    Pig pig = new Pig();
    Animal fatPigProxy =new FatPigProxy(pig);
    fatPigProxy.bark();

    Animal pigletProxy =new PigletProxy(pig);
    pigletProxy.bark();

  }

}
