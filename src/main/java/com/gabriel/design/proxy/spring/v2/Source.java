package com.gabriel.design.proxy.spring.v2;

import java.util.Random;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2021.05.10 13:05
 */
public class Source {

  public void method() {
    System.out.println("程序员找bug调试:palapalapala(键盘声)");
    try {
      Thread.sleep(new Random().nextInt(10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
