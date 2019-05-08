package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Role;

@Mapper
public interface UserRoleMapper {
	 List<Role> findByUserName(String userName);
}
