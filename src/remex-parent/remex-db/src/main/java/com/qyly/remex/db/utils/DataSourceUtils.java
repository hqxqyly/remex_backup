package com.qyly.remex.db.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.qyly.remex.db.holder.DataSourceHolder;
import com.qyly.remex.db.options.DataSourceOptions;
import com.qyly.remex.db.properties.DataSourceProperties;
import com.qyly.remex.db.source.MultiDataSourceRouting;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.utils.Assist;

/**
 * 数据源工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DataSourceUtils {
	protected static Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);
	
	/**
	 * 设置数据源key
	 * @param key
	 */
	public static void setDataSourceKey(String key) {
		DataSourceHolder.set(key);
	}
	
	/**
	 * 取得数据源key
	 * @return
	 */
	public static String getDataSourceKey() {
		return DataSourceHolder.get();
	}
	
	/**
	 * 取得多数据源中的某个数据源
	 */
	public static DataSource findMultiDataSource() {
		String key = getDataSourceKey();
		return findMultiDataSource(key);
	}
	
	/**
	 * 取得多数据源中的某个数据源
	 * @param key
	 */
	public static DataSource findMultiDataSource(String key) {
		MultiDataSourceRouting routing = DataSourceOptions.MULTI_DATA_SOURCE_ROUTING;
		DataSource dataSource = routing.getDataSource(key);
		return dataSource;
	}
	
	/**
	 * 取得多数据源中的某个数据源连接对象
	 * @param key
	 */
	public static Connection findMultiDbConnection() {
		String key = getDataSourceKey();
		return findMultiDbConnection(key);
	}
	
	/**
	 * 取得多数据源中的某个数据源连接对象
	 * @param key
	 */
	public static Connection findMultiDbConnection(String key) {
		DataSource dataSource = findMultiDataSource(key);
		Assist.notNull(dataSource, "find multi dataSource dataSource cannot be null");
		
		try {
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (Exception e) {
			throw new RemexException("find multi db connection error");
		}
	}
	
	/**
	 * 执行sql
	 * @param connection
	 * @param sql
	 */
	public static void execute(Connection connection, String sql) {
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (Exception e) {
			throw new RemexException("db execute error", e);
		}
	}
	
	/**
	 * 执行存储过程
	 * @param connection
	 * @param sql
	 */
	public static void procedure(Connection connection, String sql) {
		try {
			CallableStatement statement = connection.prepareCall(sql);
			statement.execute(sql);
			statement.close();
		} catch (Exception e) {
			throw new RemexException("db execute error", e);
		}
	}
	
	/**
	 * 提交事务
	 * @param connection
	 */
	public static void commit(Connection connection) {
		try {
			connection.commit();
			connection.close();
		} catch (Exception e) {
			throw new RemexException("db commit error", e);
		}
	}

	/**
	 * 关闭数据源
	 * @param connection
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				logger.error("db connection close error", e);
			}
		}
	}
	/**
	 * 填充数据源属性
	 * @param dataSource 数据源
	 * @param globalProperties 数据源公共属性
	 * @param properties 数据源属性
	 */
	public static void fillDataSourceProperties(DruidDataSource dataSource, DataSourceProperties globalProperties
			, DataSourceProperties properties) {
		//数据源公共属性
		fillDataSourceProperties(dataSource, globalProperties);
		//数据源属性
		fillDataSourceProperties(dataSource, properties);
	}

	/**
	 * 填充数据源属性
	 * @param dataSource 数据源
	 * @param properties 数据源属性
	 */
	public static void fillDataSourceProperties(DruidDataSource dataSource, DataSourceProperties properties) {
		if (properties == null) return;
		
		Assist.ifNotNull(properties.getUrl(), dataSource::setUrl);
		Assist.ifNotNull(properties.getUsername(), dataSource::setUsername);
		Assist.ifNotNull(properties.getPassword(), dataSource::setPassword);
		Assist.ifNotNull(properties.getDriverClassName(), dataSource::setDriverClassName);
		Assist.ifNotNull(properties.getInitialSize(), dataSource::setInitialSize);
		Assist.ifNotNull(properties.getMinIdle(), dataSource::setMinIdle);
		Assist.ifNotNull(properties.getMaxActive(), dataSource::setMaxActive);
		Assist.ifNotNull(properties.getMaxWait(), dataSource::setMaxWait);
		Assist.ifNotNull(properties.getTimeBetweenEvictionRunsMillis(), dataSource::setTimeBetweenEvictionRunsMillis);
		Assist.ifNotNull(properties.getMinEvictableIdleTimeMillis(), dataSource::setMinEvictableIdleTimeMillis);
		Assist.ifNotNull(properties.getValidationQuery(), dataSource::setValidationQuery);
		Assist.ifNotNull(properties.getTestWhileIdle(), dataSource::setTestWhileIdle);
		Assist.ifNotNull(properties.getTestOnBorrow(), dataSource::setTestOnBorrow);
		Assist.ifNotNull(properties.getTestOnReturn(), dataSource::setTestOnReturn);
		Assist.ifNotNull(properties.getRemoveAbandoned(), dataSource::setRemoveAbandoned);
		Assist.ifNotNull(properties.getRemoveAbandonedTimeoutMillis(), dataSource::setRemoveAbandonedTimeoutMillis);
		Assist.ifNotNull(properties.getLogAbandoned(), dataSource::setLogAbandoned);
		if (properties.getFilters() != null) {
			try {
				dataSource.setFilters(properties.getFilters());
			} catch (Exception e) {
				throw new RemexException("DataSource init set filters error", e);
			}
		}
	}
}
