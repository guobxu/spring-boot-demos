package me.codetalk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import me.codetalk.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guobiao.xu on 2018/6/6.
 */
@RestController
public class HelloController {

    @Reference(timeout = 5000, group = "v1")
    private HelloService helloService;

    @Reference(timeout = 5000, group = "v2")
    private HelloService helloServiceV2;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name) {
        return helloService.sayHello(name);
    }

    @RequestMapping(value = "/v2/hello", method = RequestMethod.GET)
    public String helloV2(@RequestParam("name") String name) {
        return helloServiceV2.sayHello(name);
    }

}
