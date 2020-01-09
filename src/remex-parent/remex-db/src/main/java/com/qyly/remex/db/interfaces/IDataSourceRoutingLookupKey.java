package com.qyly.remex.db.interfaces;

/**
 * 获取数据源路由维度值
 * 
 * @author Qiaoxin.Hong
 *
 */
public interface IDataSourceRoutingLookupKey {

	/**
	 * 获取数据源路由维度值
	 * @return
	 */
	String getLookupKey();
	
	/**
	 * 设置数据源路由维度值
	 * @param key
	 */
	default void setLookupKey(String key) {
		
	}
}
