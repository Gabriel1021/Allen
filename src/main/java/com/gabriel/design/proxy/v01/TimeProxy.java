package com.gabriel.design.proxy.v01;

/**
 * @author : zouxiaoxiang
 * @description :仔猪
 * @date : 2021.05.10 13:09
 */
public class TimeProxy implements SourceAble {

  private SourceAble sourceAble;

  public TimeProxy(SourceAble sourceAble){
    this.sourceAble = sourceAble;
  }

  public TimeProxy(){
  }

  @Override
  public void method() {
    before();

    sourceAble.method();

    atfer();
  }

  private void before() {
    System.out.println("性能压力测试");
  }

  private void atfer() {
    System.out.println("计算性能压力测试时间");
  }
}
