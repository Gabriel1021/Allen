package com.gabriel.design.proxy.v01;

public class Test {

  public static void main(String[] args) {
    Source source = new Source();
    SourceAble logProxy =new LogProxy(source);
    SourceAble timeProxy =new TimeProxy(logProxy);
    timeProxy.method();
  }

}
