package com.qyly.remex.db.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qyly.remex.utils.Assist;

/**
 * 多数据源属性上下文
 * 
 * @author Qiaoxin.Hong
 *
 */
public class MultiDataSourceContext {
	
	/** DataSource properties配置文件前缀 */
	public static final String MULTI_DATA_SOURCE_PREFIX = "jdbc.multidb";
	
	/** 数据源公共属性 */
	protected DataSourceProperties global;

	/** 数据源属性上下文 */
	protected Map<String, DataSourceProperties> context = new HashMap<>();
	
	/**
	 * 查询数据源key列表
	 * @return
	 */
	public List<String> queryKeyList() {
		return Assist.keyList(context);
	}

	public DataSourceProperties getGlobal() {
		return global;
	}

	public void setGlobal(DataSourceProperties global) {
		this.global = global;
	}

	public Map<String, DataSourceProperties> getContext() {
		return context;
	}

	public void setContext(Map<String, DataSourceProperties> context) {
		this.context = context;
	}
}
