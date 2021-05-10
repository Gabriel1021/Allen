package com.gabriel.design.proxy;

/**
 * @author : zouxiaoxiang
 * @description :仔猪
 * @date : 2021.05.10 13:09
 */
public class PigletProxy implements Animal {

  private Pig pig;

  public PigletProxy(Pig pig){
    this.pig = pig;
  }

  public PigletProxy(){
  }

  @Override
  public void bark() {
    before();

    pig.bark();

    atfer();
  }

  private void before() {
    System.out.println("小猪叫之前的准备工作");
  }

  private void atfer() {
    System.out.println("小猪叫之后的应对措施");
  }
}
