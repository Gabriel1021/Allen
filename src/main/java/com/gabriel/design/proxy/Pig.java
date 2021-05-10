package com.gabriel.design.proxy;

import java.util.Random;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2021.05.10 13:05
 */
public class Pig implements Animal {

  @Override
  public void bark() {
    System.out.println("肥猪：嗷嗷、哼哼、哼唧、呼噜、呼噜噜");
    try {
      Thread.sleep(new Random().nextInt(10000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
