package com.gabriel.design.proxy;

/**
 * @author : zouxiaoxiang
 * @description : 代理类
 * @date : 2021.05.10 13:10
 */
public class TimeProxy implements  SourceAble{

  private Source source;

  public TimeProxy(Source source){
    this.source = source;
  }

  public TimeProxy(){
  }

  @Override
  public void method() {

    long startTime = System.currentTimeMillis();
    before();

    source.method();

    atfer(startTime);
  }



  private void before() {
    System.out.println("性能压力测试开始~~");
  }

  private void atfer(long m) {
    System.out.println("计算性能压力测试时间：" + (System.currentTimeMillis() - m));
  }


}
