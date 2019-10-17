package com.sf.demo.service;

import java.util.List;
import java.util.Map;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
public interface DataProviderService {

    List<String[]> queryData(Map<String, String> dataSource, String sql, Integer maxRows) throws Exception;

}
