package com.qyly.remex.validator.bean;

import javax.validation.ConstraintViolation;

/**
 * 验证的结果对象
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ValidatorResult {
	
	/** 是否验证成功 */
	protected boolean success = true;
	
	/** 验证失败的消息 */
	protected String message;
	
	/** 当前验证的属性 */
	protected String property;
	
	/** 当前属性值 */
	protected Object value;
	
	/** 验证框架原结果信息 */
	protected ConstraintViolation<Object> constraintViolation;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public void setConstraintViolation(ConstraintViolation<Object> constraintViolation) {
		this.constraintViolation = constraintViolation;
	}
	
	public ConstraintViolation<Object> getConstraintViolation() {
		return constraintViolation;
	}
}
