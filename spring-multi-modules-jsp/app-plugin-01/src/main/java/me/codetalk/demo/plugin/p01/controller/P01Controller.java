package me.codetalk.demo.plugin.p01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class P01Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(P01Controller.class);

    @Value("${app.env}")
    private String appEnv;

    @RequestMapping("/plugin/p01")
    public String p01Page(Map<String, Object> model) {
        model.put("message", "this is p01 page");
        model.put("appEnv", appEnv);

        return "plugin/p01/home";
    }

}
