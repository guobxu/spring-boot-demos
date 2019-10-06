package me.codetalk.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class CoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreController.class);

    @Value("${app.env}")
    private String appEnv;

    @RequestMapping("/core")
    public String corePage(Map<String, Object> model) {
        model.put("message", "this is core page");
        model.put("appEnv", appEnv);

        return "core";
    }

}
