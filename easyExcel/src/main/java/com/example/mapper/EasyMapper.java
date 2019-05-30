package com.example.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.annotation.DataSource;
import com.example.model.LoggerModel;

@Mapper
public interface EasyMapper {
	
	@Select("select * from Logger")
	List<LoggerModel> selectAll();
	
	@DataSource
	@Select("select top 10 * from Logger")
	List<Map<String,Object>> selectMaster();
	
	@DataSource("ds1")
	@Select("select * from city")
	List<Map<String,Object>> selectDS1();
	
	@DataSource("ds2")
	@Select("select top 10 * from Logger")
	List<Map<String,Object>> selectDS2();
	
}
