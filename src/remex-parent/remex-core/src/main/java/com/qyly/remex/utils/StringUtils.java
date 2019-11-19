package com.qyly.remex.utils;

/**
 * 字符串工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 是否为null或空字符串
	 * @param val
	 * @return
	 */
	public static boolean isBlank(Object val) {
		if (val == null) {
			return true;
		} else if(val instanceof String) {
			return isBlank(val.toString());
		}
		
		return false;
	}
	
	/**
	 * 是否不为null或空字符串
	 * @param val
	 * @return
	 */
	public static boolean isNotBlank(Object val) {
		return !isBlank(val);
	}
	
	/**
	 * 默认字符串
	 * @param val
	 * @return
	 */
	public static String defaultString(Object val) {
		return val == null ? "" : val.toString();
	}
	
	/**
	 * 取得字符串
	 * @param val
	 * @return
	 */
	public static String getString(Object val) {
		return val == null ? null : val.toString();
	}
}
