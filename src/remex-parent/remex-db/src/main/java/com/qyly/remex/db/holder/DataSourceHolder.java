package com.qyly.remex.db.holder;

/**
 * 数据源当前线程值
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DataSourceHolder {
	/** 数据源当前线程值 */
	private static final InheritableThreadLocal<String> holder = new InheritableThreadLocal<>();
	
	/**
	 * 设置数据源当前线程值
	 * @param value
	 */
	public static void set(String value) {
		holder.set(value);
	}
	
	/**
	 * 取得数据源当前线程值
	 * @return
	 */
	public static String get() {
		return holder.get();
	}
}
