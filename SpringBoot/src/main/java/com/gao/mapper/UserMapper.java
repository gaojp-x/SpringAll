package com.gao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	
	@Select("select * from acegi_users where username=#{acc} and truename=#{pwd}")
	Map<String,Object> selectUser(Map<String, String> map);
	
}
