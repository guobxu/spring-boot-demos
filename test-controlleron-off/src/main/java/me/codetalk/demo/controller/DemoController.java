package me.codetalk.demo.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creator: 01380994
 * Date: 2019/3/11
 */
@RestController
@ConditionalOnExpression("${demo.controller.enabled:true}")
public class DemoController {

    @RequestMapping(value = "sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam("user") String user) {
        return String.format("Hello %s", user);
    }

}
