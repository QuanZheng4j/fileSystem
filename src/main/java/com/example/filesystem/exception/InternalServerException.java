package com.example.filesystem.exception;

/**
 * @author: zq
 * @Description: 内部服务异常
 * @date 2018/8/31 10:07
 */
public class InternalServerException extends BusinessException {

	private static final long serialVersionUID = 2659909836556958676L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InternalServerException(String msg, Throwable cause, Object... objects) {
		super(msg, cause, objects);
	}

	public InternalServerException(String msg) {
		super(msg);
	}

	public InternalServerException(String formatMsg, Object... objects) {
		super(formatMsg, objects);
	}

}
