package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.UserInfo;

@Mapper
public interface UserMapper {

	UserInfo findByUserName(String userName);

}
