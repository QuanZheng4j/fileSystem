package com.example.filesystem.exception;



import com.example.filesystem.enums.ResultCode;

/**
 * @author: zq
 * @Description: 数据没有找到异常
 * @date 2018/8/31 10:07
 */
public class DataNotFoundException extends BusinessException {

	private static final long serialVersionUID = 3721036867889297081L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(Object data) {
		super();
		super.data = data;
	}

	public DataNotFoundException(ResultCode resultCode) {
		super(resultCode);
	}

	public DataNotFoundException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public DataNotFoundException(String msg) {
		super(msg);
	}

	public DataNotFoundException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
