package com.qyly.remex.db.component;

import com.qyly.remex.db.holder.DataSourceHolder;
import com.qyly.remex.db.interfaces.IDataSourceRoutingLookupKey;

/**
 * 默认获取数据源路由维度值
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DefaultDataSourceRoutingLookupKey implements IDataSourceRoutingLookupKey {

	/**
	 * 获取数据源路由维度值
	 */
	@Override
	public String getLookupKey() {
		return DataSourceHolder.get();
	}
	
	/**
	 * 设置数据源路由维度值
	 */
	@Override
	public void setLookupKey(String key) {
		DataSourceHolder.set(key);
	}
}
