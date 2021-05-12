package com.gabriel.design.proxy;

/**
 * @author : zouxiaoxiang
 * @description :仔猪
 * @date : 2021.05.10 13:09
 */
public class LogProxy implements SourceAble {

  private Source source;

  public LogProxy(Source source){
    this.source = source;
  }

  public LogProxy(){
  }

  @Override
  public void method() {
    before();

    source.method();

    atfer();
  }

  private void before() {
    System.out.println("执行方法之前的日志");
  }

  private void atfer() {
    System.out.println("执行方法之后的日志");
  }
}
