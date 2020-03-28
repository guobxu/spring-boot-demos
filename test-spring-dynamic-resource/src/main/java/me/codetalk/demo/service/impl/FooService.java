package me.codetalk.demo.service.impl;

import me.codetalk.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

@Service("helloService")
@ConditionalOnExpression("#{'foo'.equals('${helloService}')}")
public class FooService implements HelloService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooService.class);

    @Value("${helloTo}")
    private String helloTo;

    public void sayHello() {
        LOGGER.info("Hello from {}, to {}", "foo", helloTo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("FooService inited");
    }

}
