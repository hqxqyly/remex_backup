package com.qyly.remex.utils;

import java.util.Collection;

import com.qyly.remex.exception.RemexException;

/**
 * 断言工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class Assert {

	/**
	 * 不能为null
	 * @param val
	 */
	public static void notNull(Object val) {
		notNull(val, "The argument must not be null");
	}
	
	/**
	 * 不能为null
	 * @param val
	 * @param msg
	 */
	public static void notNull(Object val, String msg) {
		if (val == null) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 字符串不能为空
	 * @param val
	 */
	public static void notBlank(String val) {
		notBlank(val, "The argument must not be blank");
	}
	
	/**
	 * 字符串不能为空
	 * @param val
	 * @param msg
	 */
	public static void notBlank(String val, String msg) {
		if (StringUtils.isBlank(val)) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 集合不能为空
	 * @param coll
	 */
	public static void notEmpty(Collection<?> coll) {
		notEmpty(coll, "Collection must contain elements");
	}
	
	/**
	 * 集合不能为空
	 * @param coll
	 * @param msg
	 */
	public static void notEmpty(Collection<?> coll, String msg) {
		if (CollectionUtils.isEmpty(coll)) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 必须为null
	 * @param val
	 */
	public static void isNull(Object val) {
		isNull(val, "The argument must be null");
	}
	
	/**
	 * 必须为null
	 * @param val
	 * @param msg
	 */
	public static void isNull(Object val, String msg) {
		if (val != null) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 字符串必须为空
	 * @param val
	 */
	public static void isBlank(String val) {
		isBlank(val, "The argument must be blank");
	}
	
	/**
	 * 字符串必须为空
	 * @param val
	 * @param msg
	 */
	public static void isBlank(String val, String msg) {
		if (StringUtils.isNotBlank(val)) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 集合必须为空
	 * @param coll
	 */
	public static void isEmpty(Collection<?> coll) {
		isEmpty(coll, "Collection must not contain elements");
	}
	
	/**
	 * 集合必须为空
	 * @param coll
	 * @param msg
	 */
	public static void isEmpty(Collection<?> coll, String msg) {
		if (CollectionUtils.isNotEmpty(coll)) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 必须为true，null => false
	 * @param val
	 */
	public static void isTrue(Boolean val) {
		isTrue(val, "The argument must not be true");
	}
	
	/**
	 * 必须为true，null => false
	 * @param val
	 * @param msg
	 */
	public static void isTrue(Boolean val, String msg) {
		if (val == null || !val) {
			throw new RemexException(msg);
		}
	}
	
	/**
	 * 必须为false，null => false
	 * @param val
	 */
	public static void isFalse(Boolean val) {
		isTrue(val, "The argument must not be false");
	}
	
	/**
	 * 必须为false，null => false
	 * @param val
	 * @param msg
	 */
	public static void isFalse(Boolean val, String msg) {
		if (val != null && val) {
			throw new RemexException(msg);
		}
	}
}
