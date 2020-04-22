package com.example.filesystem.handler;

import com.example.filesystem.annotations.ResponseResult;
import com.example.filesystem.interceptor.ResponseResultInterceptor;
import com.example.filesystem.model.result.DefaultErrorResult;
import com.example.filesystem.model.result.PlatformResult;
import com.example.filesystem.model.result.Result;
import com.example.filesystem.util.JsonUtil;
import com.example.filesystem.util.RequestContextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: zq
 * @Description:  接口响应体处理器
 * @date 2018/8/31 10:07
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		ResponseResult responseResultAnn = (ResponseResult) RequestContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
		return responseResultAnn == null ? false : true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		ResponseResult responseResultAnn = (ResponseResult) RequestContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

		Class<? extends Result> resultClazz = responseResultAnn.value();

		if (resultClazz.isAssignableFrom(PlatformResult.class)) {
			if (body instanceof DefaultErrorResult) {
				DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
				return PlatformResult.builder()
						.code(defaultErrorResult.getCode())
						.msg(defaultErrorResult.getMessage())
						.data(defaultErrorResult.getErrors())
						.build();
			} else if (body instanceof String) {
				return JsonUtil.object2Json(PlatformResult.success(body));
			}

			return PlatformResult.success(body);
		}

		return body;
	}

}
