package com.gabriel.utils;

import com.gabriel.service.IService;
import java.util.Iterator;
import java.util.ServiceLoader;
import sun.misc.Service;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2020.12.08 11:12
 */
public class CommonTest {

  public static void main(String[] args) {
    Iterator<IService> providers = Service.providers(IService.class);
    ServiceLoader<IService> load = ServiceLoader.load(IService.class);

    while(providers.hasNext()) {
      IService ser = providers.next();
      ser.executing();
    }
    System.err.println("--------------------------------");
    Iterator<IService> iterator = load.iterator();
    while(iterator.hasNext()) {
      IService ser = iterator.next();
      ser.executing();
    }
  }
}
