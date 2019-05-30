package com.example.aop;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description 注册动态数据源
 * 初始化数据源和提供了执行动态切换数据源的工具类
 * EnvironmentAware（获取配置文件配置的属性值）
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    //指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
    private static final String DataSource_Master = "com.zaxxer.hikari.HikariDataSource";
    //默认数据源
    private DataSource default_DataSource;
    //用户自定义数据源
    private Map<String, DataSource> slaveDataSources = new HashMap<>();
    
    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
        //System.out.println("#主数据源#"+DataSource_Default);
        initslaveDataSources(environment);
       // System.out.println("#子数据源池#"+slaveDataSources);
    }

    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("type", env.getProperty("spring.datasource.type"));
        dsMap.put("driver", env.getProperty("spring.datasource.driver"));
        dsMap.put("url", env.getProperty("spring.datasource.url"));
        dsMap.put("username", env.getProperty("spring.datasource.username"));
        dsMap.put("password", env.getProperty("spring.datasource.password"));
        //System.out.println("主数据源"+dsMap);
        default_DataSource = buildDataSource(dsMap);
    }


    private void initslaveDataSources(Environment env) {
        // 读取配置文件获取更多数据源
        String dsPrefixs = env.getProperty("slave.datasource.names");
        for (String dsPrefix : dsPrefixs.split(",")) {
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            
            String datasourceType = env.getProperty("slave.datasource." + dsPrefix + ".type");
            if(datasourceType==null) {//如果自数据源未配置则使用主数据源的配置
            	datasourceType = env.getProperty("spring.datasource.type");
            }
            dsMap.put("type", datasourceType);
            
            dsMap.put("driver", env.getProperty("slave.datasource." + dsPrefix + ".driver"));
            dsMap.put("url", env.getProperty("slave.datasource." + dsPrefix + ".url"));
            dsMap.put("username", env.getProperty("slave.datasource." + dsPrefix + ".username"));
            dsMap.put("password", env.getProperty("slave.datasource." + dsPrefix + ".password"));
            //System.out.println("更多数据源"+dsMap);
            DataSource ds = buildDataSource(dsMap);
            slaveDataSources.put(dsPrefix, ds);
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //添加默认数据源
        targetDataSources.put("dataSource", this.default_DataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        for (String key : slaveDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", default_DataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
        logger.info("动态数据源注册");
    }

    @SuppressWarnings("all")
    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                type = DataSource_Master;// 默认DataSource
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName( (String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder
            		.create()
            		.type(dataSourceType)
            		.driverClassName(driverClassName)
            		.url(url)
                    .username(username)
                    .password(password);
            //logger.info("驱动构建"+factory.build());
            
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("数据源配置可能有问题");
        }
        return null;
    }
}

