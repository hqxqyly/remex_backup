package com.qyly.remex.exception;

/**
 * remex运行异常类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RemexException() {
		super();
	}

	public RemexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RemexException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemexException(String message) {
		super(message);
	}

	public RemexException(Throwable cause) {
		super(cause);
	}
}
