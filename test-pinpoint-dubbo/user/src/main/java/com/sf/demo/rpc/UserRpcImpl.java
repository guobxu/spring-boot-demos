package com.sf.demo.rpc;

import com.sf.demo.entity.User;
import com.sf.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creator: 01380994
 * Date: 2019/4/18
 */
@Service("userRpc")
public class UserRpcImpl implements UserRpc {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRpcImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUser(String userId) {
        return userMapper.selectUserById(userId);
    }

}
