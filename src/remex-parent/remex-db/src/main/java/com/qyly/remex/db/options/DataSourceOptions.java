package com.qyly.remex.db.options;

import com.qyly.remex.db.component.DefaultDataSourceRoutingLookupKey;
import com.qyly.remex.db.interfaces.IDataSourceRoutingLookupKey;
import com.qyly.remex.db.source.MultiDataSourceRouting;

/**
 * db配置项
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DataSourceOptions {

	/** 获取数据源路由维度值 */
	public static IDataSourceRoutingLookupKey LOOKUP_KEY = new DefaultDataSourceRoutingLookupKey();
	
	/** 多数据源路由器 */
	public static MultiDataSourceRouting MULTI_DATA_SOURCE_ROUTING = null; 
}
