package com.example.filesystem.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.filesystem.util.IpUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @auther: zq
 * @Date: 2018/8/12 15:32
 * @Description: 接口耗时拦截切面
 */
@Aspect
@Slf4j
@Component
public class RestControllerAspect {

	@Around("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		boolean logFlag = this.needToLog(method);
		if (!logFlag) {
			return joinPoint.proceed();
		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = IpUtil.getRealIp(request);
		String methodName = this.getMethodName(joinPoint);
		String params = this.getParamsJson(joinPoint);

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if(result==null){
                //如果切到了 没有返回类型的void方法，这里直接返回
                return null;
            }
            long end = System.currentTimeMillis();

            synchronized (this){
				log.info("============================================================================================");
				log.info("IP:[ {} ]", ip);
				log.info("方法:[ {} ]", methodName);
				log.info("参数:[ {} ]", params);
				log.info("耗时:[ {} ms ]", end-start);
				log.info("============================================================================================");
			}

            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
			log.info("====around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            throw e;
        }

    }

	private String getMethodName(ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		String shortMethodNameSuffix = "(..)";
		if (methodName.endsWith(shortMethodNameSuffix)) {
			methodName = methodName.substring(0, methodName.length() - shortMethodNameSuffix.length());
		}
		return methodName;
	}

	private String getParamsJson(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		StringBuilder sb = new StringBuilder();
		for (Object arg : args) {
			//移除敏感内容
			String paramStr;
			if (arg instanceof HttpServletResponse) {
				paramStr = HttpServletResponse.class.getSimpleName();
			} else if (arg instanceof HttpServletRequest) {
				paramStr = HttpServletRequest.class.getSimpleName();
			} else if (arg instanceof MultipartFile) {
				long size = ((MultipartFile) arg).getSize();
				paramStr = MultipartFile.class.getSimpleName() + " size:" + size;
			} else {
				paramStr = this.deleteSensitiveContent(arg);
			}
			sb.append(paramStr).append(",");
		}
		if(StringUtils.isBlank(sb)){
			return sb.toString();
		}else {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
	}

	/**
	 * 删除参数中的敏感内容
	 * @param obj 参数对象
	 * @return 去除敏感内容后的参数对象
	 */
	private String deleteSensitiveContent(Object obj) {
		JSONObject jsonObject = new JSONObject();
		if (obj == null || obj instanceof Exception) {
			return jsonObject.toJSONString();
		}

		try {
			String param = JSON.toJSONString(obj);
			jsonObject = JSONObject.parseObject(param);
			List<String> sensitiveFieldList = this.getSensitiveFieldList();
			for (String sensitiveField : sensitiveFieldList) {
				if (jsonObject.containsKey(sensitiveField)) {
					jsonObject.put(sensitiveField, "******");
				}
			}
		} catch (ClassCastException e) {
			return String.valueOf(obj);
		}
		return jsonObject.toJSONString();
	}

	/**
	 * 敏感字段列表
	 */
	private List<String> getSensitiveFieldList() {
		List<String> sensitiveFieldList = Lists.newArrayList();
		sensitiveFieldList.add("pwd");
		sensitiveFieldList.add("password");
		return sensitiveFieldList;
	}

	private boolean needToLog(Method method) {
//		//GET请求不记录日志
//		return method.getAnnotation(GetMapping.class) == null
//				&& !method.getDeclaringClass().equals(GlobalExceptionHandler.class);
		return true;
	}
}
