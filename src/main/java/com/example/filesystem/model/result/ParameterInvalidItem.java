package com.example.filesystem.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zq
 * @Description: 参数无效项
 * @date 2018/8/31 10:07
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParameterInvalidItem {

	/**
	 * 无效字段的名称
	 */
	private String fieldName;

	/**
	 * 错误信息
	 */
	private String message;

}
