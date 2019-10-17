package com.sf.demo.rpc;

import com.alibaba.fastjson.JSONObject;
import com.sf.demo.dto.ResponseDto;
import com.sf.demo.entity.User;
import com.sf.demo.service.DataProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@Service("dataProviderRpc")
public class DataProviderRpcImpl implements DataProviderRpc {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProviderRpcImpl.class);

    @Autowired
    private DataProviderService dataProviderService;

    @Autowired
    private UserRpc userRpc;

    private static final String DATASOURCE_CONFIG = "{\"driver\":\"com.mysql.jdbc.Driver\",\"jdbcurl\":\"jdbc:mysql://10.202.45.32:3306/?useUnicode=true&characterEncoding=UTF-8&useSSL=true&tinyInt1isBit=false\",\"password\":\"passwd123\",\"username\":\"bdpa\"}";

    @Override
    public ResponseDto queryData(String userId, String sql, Integer maxRows) {
        try {
            User userInfo = userRpc.findUser(userId);

            Map<String, String> dataSource = JSONObject.parseObject(DATASOURCE_CONFIG, Map.class);
            List<String[]> result = dataProviderService.queryData(dataSource, sql, maxRows);

            Map<String, Object> retData = new HashMap<>();
            retData.put("user", userInfo);
            retData.put("data", result);

            return ResponseDto.success(retData);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            return ResponseDto.error("查询失败, 错误消息: " + ex.getMessage());
        }

    }

}
