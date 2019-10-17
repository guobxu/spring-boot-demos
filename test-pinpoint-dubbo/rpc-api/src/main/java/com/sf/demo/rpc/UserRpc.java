package com.sf.demo.rpc;

import com.sf.demo.entity.User;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
public interface UserRpc {

    User findUser(String userId);

}
