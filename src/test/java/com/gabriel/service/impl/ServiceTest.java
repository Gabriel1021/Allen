package com.gabriel.service.impl;


import com.gabriel.Test01Application;
import com.gabriel.service.IHelpCategoryService;
import com.gabriel.service.IService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : zouxiaoxiang
 * @description :
 * @date : 2020.12.18 14:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test01Application.class)
public class ServiceTest {

  @Autowired
  @Qualifier("Service1")
  private IService service1;

  @Autowired
  private IHelpCategoryService helpCategoryService;

  @Test
  public void test() {
    service1.executing();
  }

  public void tr(){

  }
}