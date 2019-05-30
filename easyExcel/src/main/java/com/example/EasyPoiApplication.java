package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import com.example.aop.DynamicDataSourceRegister;

@SpringBootApplication
@Import(DynamicDataSourceRegister.class)
public class EasyPoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyPoiApplication.class, args);
	}

}
