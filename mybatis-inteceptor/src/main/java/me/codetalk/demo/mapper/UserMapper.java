package me.codetalk.demo.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.demo.pojo.User;
import me.codetalk.mybatis.annotation.Shardby;

public interface UserMapper {

	@Shardby("user")
	public void insertUser(@Param("user") User user);
	
	@Shardby("id")
	public User selectUser(@Param("id") Long id);
	
	@Shardby("user")
	public void updateUser(@Param("user") User user);
	
	@Shardby("id")
	public void deleteUser(@Param("id") Long id);
	
}
