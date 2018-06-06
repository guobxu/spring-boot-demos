package me.codetalk.test.controller;

import me.codetalk.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guobiao.xu on 2018/6/6.
 */
@RestController
public class TestController {

    @RequestMapping(value="/api/" + Constants.API_VERS + "/test", method = RequestMethod.GET)
    public String test() {
        return "test api " + Constants.API_VERS;
    }

}
