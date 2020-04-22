package com.example.filesystem.exception;


import com.example.filesystem.enums.ResultCode;

/**
 * @author: zq
 * @Description: 远程访问异常
 * @date 2018/8/31 10:07
 */
public class RemoteAccessException extends BusinessException {

	private static final long serialVersionUID = -832464574076215195L;

	public RemoteAccessException() {
		super();
	}

	public RemoteAccessException(Object data) {
		super.data = data;
	}

	public RemoteAccessException(ResultCode resultCode) {
		super(resultCode);
	}

	public RemoteAccessException(ResultCode resultCode, Object data) {
		super(resultCode, data);
	}

	public RemoteAccessException(String msg) {
		super(msg);
	}

	public RemoteAccessException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
