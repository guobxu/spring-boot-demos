package me.codetalk.demo.plugin.p02.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guobxu
 * @createTime 5/10/2019
 * @description
 */
@RestController
public class P02Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(P02Controller.class);

    @RequestMapping(value = "/plugin/p02/info", method = RequestMethod.GET)
    public Map<String, String> getP02Info() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "This is from plugin P02");

        return info;
    }

}
