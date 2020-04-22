package com.example.filesystem.config;

import com.example.filesystem.dao.CurdMapper;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author: zq
 * @Description: 扫描配置
 * @date 2018/8/30 17:39
 */
@Configuration
//必须在MyBatisConfig注册后再加载MapperScannerConfigurer，否则会报错
@AutoConfigureAfter({MybatisAutoConfiguration.class, MybatisConfig.class})
public class MyBatisMapperScannerConfig {


	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.example.filesystem.dao.mapper");

		Properties properties = new Properties();
		properties.setProperty("mappers", CurdMapper.class.getName());
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		properties.setProperty("ORDER","AFTER");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}

}
