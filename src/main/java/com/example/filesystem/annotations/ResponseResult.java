package com.example.filesystem.annotations;

import com.example.filesystem.model.result.PlatformResult;
import com.example.filesystem.model.result.Result;

import java.lang.annotation.*;

/**
 * @desc 接口返回结果增强  会通过拦截器拦截后放入标记，在WebResponseBodyHandler进行结果处理
 *
 * @author zq
 * @since 2018/8/31 15:00
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseResult {

    Class<? extends Result> value() default PlatformResult.class;

}
