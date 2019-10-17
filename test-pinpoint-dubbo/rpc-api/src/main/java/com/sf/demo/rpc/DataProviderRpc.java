package com.sf.demo.rpc;

import com.sf.demo.dto.ResponseDto;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
public interface DataProviderRpc {

    ResponseDto queryData(String userId, String sql, Integer maxRows);

}
