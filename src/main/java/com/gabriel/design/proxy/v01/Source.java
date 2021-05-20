package com.gabriel.design.proxy.v01;

import java.util.Random;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2021.05.10 13:05
 */
public class Source implements SourceAble {

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
    Source source = new Source();
    SourceAble logProxy =new LogProxy(source);
    SourceAble timeProxy =new TimeProxy(logProxy);
    timeProxy.method();
  }
}
class LogProxy implements SourceAble {

  private SourceAble sourceAble;

  public LogProxy(SourceAble sourceAble){
    this.sourceAble = sourceAble;
  }

  public LogProxy(){
  }

  @Override
  public void method() {
    before();

    sourceAble.method();

    atfer();
  }


  private void before() {
    System.out.println("执行方法之前的日志: ****************");
  }

  private void atfer() {
    System.out.println("执行方法之后的日志: $$$$$$$$$$$$$$$$$");
  }


}


class TimeProxy implements SourceAble {

  private SourceAble sourceAble;

  public TimeProxy(SourceAble sourceAble){
    this.sourceAble = sourceAble;
  }

  public TimeProxy(){
  }

  @Override
  public void method() {
    long startTime = System.currentTimeMillis();
    before();

    sourceAble.method();

    atfer(startTime);
  }

  private void before() {
    System.out.println("性能压力测试开始~~");
  }

  private void atfer(long m) {
    System.out.println("计算性能压力测试时间：" + (System.currentTimeMillis() - m));
  }
}

interface SourceAble {

  void method();
}
