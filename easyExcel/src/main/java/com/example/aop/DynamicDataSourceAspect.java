package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.annotation.DataSource;

@Aspect
@Order(-10) // 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

	@Before("@annotation(dataSource)")
	public void changeDataSource(JoinPoint point, DataSource dataSource) throws Throwable {
		String dsId = dataSource.value();
		
		System.out.println("#######################");
		DynamicDataSourceContextHolder.dataSourceIds.forEach(System.out::println);
		System.out.println("#######################");
		
		if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
			DynamicDataSourceContextHolder.setDataSourceType(dsId);
			logger.debug("使用数据源:{} >", dsId, point.getSignature());
		} else {
			logger.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
		}
	}

	@After("@annotation(dataSource)")
	public void restoreDataSource(JoinPoint point, DataSource dataSource) {
		logger.debug("移除数据源 : " + dataSource.value() + " > " + point.getSignature());
		DynamicDataSourceContextHolder.clearDataSourceType();

	}

}
