package com.sf.demo.service.impl;

import com.sf.demo.locale.LocaleMessages;
import com.sf.demo.service.GoodbyeService;
import com.sf.demo.service.HelloByeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@Service("helloByeService")
public class HelloByeServiceImpl implements HelloByeService {

//    private static final String MSG_CODE_ERROR_DS_CONNECT = "error_datasource_connect";

    @Autowired
    private LocaleMessages localeMesgs;

    @Autowired
    private GoodbyeService goodbyeService;

    @Override
    public String sayHello(String name) {
        return String.format(localeMesgs.getByRpcContext("hello"), name);
    }

    @Override
    public String sayGoodbye(String name) {
        return goodbyeService.sayGoodbye(name);
    }
}
