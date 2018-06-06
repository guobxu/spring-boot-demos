package me.codetalk.service.impl;

import me.codetalk.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by guobiao.xu on 2018/6/6.
 */
@Service
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = HelloService.class, group = "v2")
public class HelloServiceImpl implements HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(String name) {
        LOGGER.info("sayHello-v2 {}", name);

        return "V2-Hello " + name;
    }

}
