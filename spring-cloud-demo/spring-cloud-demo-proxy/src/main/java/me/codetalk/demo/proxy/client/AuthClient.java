package me.codetalk.demo.proxy.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("auth")
public interface AuthClient {

    @RequestMapping(value = "/auth/user/{username}", method = RequestMethod.GET)
    String doGet(@PathVariable("username") String username);
	
}
