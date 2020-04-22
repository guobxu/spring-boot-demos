package com.sf.demo.service.impl;

import com.sf.demo.locale.LocaleMessages;
import com.sf.demo.service.GoodbyeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creator: 01380994
 * Date: 2020/4/22
 */
@Service("goodbyeService")
public class GoodbyeServiceImpl implements GoodbyeService {

    @Autowired
    private LocaleMessages localeMesgs;

    @Override
    public String sayGoodbye(String name) {
        return String.format(localeMesgs.getByRpcContext("goodbye"), name);
    }

}
