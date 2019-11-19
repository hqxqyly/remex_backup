package com.qyly.remex.utils;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContext工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ApplicationContextUtils {

	/** ApplicationContext */
	private static ApplicationContext context;
	
	/**
	 * 设置ApplicationContext
	 * @param context
	 */
	public static void setContext(ApplicationContext context) {
		ApplicationContextUtils.context = context;
	}
	
	/**
	 * 取得ApplicationContext
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	
	/**
	 * 取得bean
	 * @param <T>
	 * @param beanClass
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return getContext().getBean(beanClass);
	}
}
