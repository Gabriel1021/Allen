package com.gabriel.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @RequestMapping("hello")
  public String hello() {
    return "Hello this is my first springboot demo";
  }
}