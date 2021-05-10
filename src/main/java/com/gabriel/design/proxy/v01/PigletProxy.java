package com.gabriel.design.proxy.v01;

/**
 * @author : zouxiaoxiang
 * @description :仔猪
 * @date : 2021.05.10 13:09
 */
public class PigletProxy implements Animal {

  private Animal animal;

  public PigletProxy(Animal animal){
    this.animal = animal;
  }

  public PigletProxy(){
  }

  @Override
  public void bark() {
    before();

    animal.bark();

    atfer();
  }

  private void before() {
    System.out.println("小猪叫之前的准备工作");
  }

  private void atfer() {
    System.out.println("小猪叫之后的应对措施");
  }
}
