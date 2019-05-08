package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.model.Permission;

@Mapper
public interface UserPermissionMapper {
	List<Permission> findByUserName(String userName);
}
