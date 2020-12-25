package com.gabriel.service.impl;

import com.gabriel.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2020.12.08 11:32
 */
@Service("Service1")
public class Service1 implements IService {

  @Override
  public void executing() {
    System.err.println("Service1.execute()");
  }

}
