package com.example.filesystem.config;

import com.example.filesystem.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author: zq
 * @Description: 响应结果拦截器配置
 * @date 2018/8/31 9:38
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Resource
	private ResponseResultInterceptor responseResultInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String apiUri = "/**";
		//响应结果控制拦截
		registry.addInterceptor(responseResultInterceptor).addPathPatterns(apiUri);
	}

}