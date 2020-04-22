package com.sf.demo.controller;

import com.sf.demo.service.HelloByeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private HelloByeService helloByeService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(value = "name") String name, HttpServletRequest request) {
        String s = helloByeService.sayHello(name);

        return s;
    }

    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public String sayGoodbye(@RequestParam(value = "name") String name, HttpServletRequest request) {
        String s = helloByeService.sayGoodbye(name);

        return s;
    }

}
