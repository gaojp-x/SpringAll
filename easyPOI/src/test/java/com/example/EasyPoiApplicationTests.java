package com.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mapper.EasyMapper;
import com.example.model.LoggerModel;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyPoiApplicationTests {

	@Autowired
	EasyMapper easyMapper;
	
	@Test
	public void contextLoads() {
		System.out.println(easyMapper.selectById(1111));
	}

}
