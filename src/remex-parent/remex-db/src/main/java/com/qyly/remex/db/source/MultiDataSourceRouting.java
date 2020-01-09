package com.qyly.remex.db.source;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.qyly.remex.db.interfaces.IDataSourceRoutingLookupKey;
import com.qyly.remex.db.options.DataSourceOptions;
import com.qyly.remex.utils.Assist;

/**
 * 多数据源路由器
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MultiDataSourceRouting extends AbstractRoutingDataSource {
	
	/** 数据源列表 */
	protected Map<Object, Object> dataSourceMap = new HashMap<>();
	
	/** 第一个数据源key */
	protected Object firstDataSourceKey;
	
	public MultiDataSourceRouting() {
		DataSourceOptions.MULTI_DATA_SOURCE_ROUTING = this;
	}
	
	@Override
	public void afterPropertiesSet() {
		setTargetDataSources(dataSourceMap);
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		Object lookupKey = Assist.ifNotNullFn(DataSourceOptions.LOOKUP_KEY, IDataSourceRoutingLookupKey::getLookupKey);
		return Assist.defaultVal(lookupKey, getDefaultDataSourceKey());
	}
	
	/**
	 * 添加数据源
	 * @param key
	 * @param dataSource
	 */
	public void addDataSource(Object key, Object dataSource) {
		Assist.notNull(key, "key cannot be null");
		Assist.notNull(dataSource, "dataSource cannot be null");
		
		dataSourceMap.put(key, dataSource);
		
		if (firstDataSourceKey == null) {
			firstDataSourceKey = key;
		}
	}
	
	/**
	 * 取得默认的数据源key
	 * @return
	 */
	protected Object getDefaultDataSourceKey() {
		return Assist.defaultVal(firstDataSourceKey, dataSourceMap.keySet().iterator().next());
	}
	
	/**
	 * 取得数据源
	 * @param key
	 * @return
	 */
	public DataSource getDataSource(Object key) {
		DataSource dataSource = (DataSource) dataSourceMap.get(key);
		return dataSource;
	}
}
