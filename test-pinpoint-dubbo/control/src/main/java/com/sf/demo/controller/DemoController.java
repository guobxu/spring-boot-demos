package com.sf.demo.controller;

import com.sf.demo.dto.ResponseDto;
import com.sf.demo.rpc.DataProviderRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DataProviderRpc dataProviderRpc;

    @RequestMapping(value="/queryData", method = RequestMethod.GET)
    public ResponseDto queryData(@RequestParam("userId") String userId,
                                 @RequestParam("sql") String sql,
                                 @RequestParam("maxRows") Integer maxRows,
                                 HttpServletRequest request) {
        ResponseDto resp = dataProviderRpc.queryData(userId, sql, maxRows);

        return resp;
    }

}
