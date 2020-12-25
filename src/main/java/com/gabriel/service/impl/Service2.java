package com.gabriel.service.impl;

import com.gabriel.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2020.12.08 11:41
 */
@Service("Service2")
public class Service2 implements IService {

  @Override
  public void executing() {
    System.err.println("Service2.execute()");
  }

}
