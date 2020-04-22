package com.example.filesystem.annotations;


import java.lang.annotation.*;

/**
 * @desc 别名注解 用来为类的字段添加别名（备注：可重复注解，也可以为一个别名指定多个源类）
 * 
 * @author zq
 * @since 2018年8月30日 09:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(FieldAlias.FieldAliases.class)
public @interface FieldAlias {

	String value();

	Class<?>[] sourceClass() default { };

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface FieldAliases {
		FieldAlias[] value();
	}
}
