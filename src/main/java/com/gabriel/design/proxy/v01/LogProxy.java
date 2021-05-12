package com.gabriel.design.proxy.v01;

/**
 * @author : zouxiaoxiang
 * @description : 静态代理类变形v01
 * @date : 2021.05.10 13:10
 */
public class LogProxy implements SourceAble {

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
    System.out.println("执行方法之前的日志");
  }

  private void atfer() {
    System.out.println("执行方法之后的日志");
  }


}
