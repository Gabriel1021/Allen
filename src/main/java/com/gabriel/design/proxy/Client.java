package com.gabriel.design.proxy;

public class Client {

  public static void main(String[] args) {

    Source source = new Source();
    SourceAble timeProxy =new TimeProxy(source);
    timeProxy.method();

    SourceAble logProxy =new LogProxy(source);
    logProxy.method();

  }

}
