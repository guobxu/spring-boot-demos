package me.codetalk.demo.service.impl;

import me.codetalk.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

@Service("helloService")
@ConditionalOnExpression("#{'bar'.equals('${helloService}')}")
public class BarService implements HelloService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarService.class);

    public void sayHello() {
        LOGGER.info("Hello from {}", "bar");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("BarService inited");
    }

}
