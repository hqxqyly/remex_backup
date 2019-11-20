package com.qyly.remex.validator.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

import com.qyly.remex.utils.CollectionUtils;
import com.qyly.remex.validator.bean.ValidatorResult;

/**
 * 验证工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ValidatorUtils {

	/** 验证器 */
	protected static Validator validator = null;
	
	static {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
			.configure()
			.addProperty("hibernate.validator.fail_fast", "true")  //快速模式，一个验证失败就结束
			.buildValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	/**
	 * 验证
	 * @param obj
	 */
	public static ValidatorResult validate(Object obj) {
		ValidatorResult result = new ValidatorResult();
		
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
		if (CollectionUtils.isNotEmpty(constraintViolations)) {
			ConstraintViolation<Object> violation = constraintViolations.iterator().next();
			result.setSuccess(false);
			result.setMessage(violation.getMessage());
			result.setProperty(violation.getPropertyPath().toString());
			result.setValue(violation.getInvalidValue());
			result.setConstraintViolation(violation);
		}
		
		return result;
	}
}