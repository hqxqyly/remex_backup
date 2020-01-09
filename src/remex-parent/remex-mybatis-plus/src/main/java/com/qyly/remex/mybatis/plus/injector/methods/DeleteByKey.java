package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.qyly.remex.mybatis.plus.constant.RemexSqlMethod;

/**
 * mybatis plus全局公共方法 - 根据某字段值删除数据
 * 
 * @author Qiaoxin.Hong
 *
 */
public class DeleteByKey extends BaseMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		//逻辑处理
		if (tableInfo.isLogicDelete()) {
			//取得逻辑删除的拼接sql
			String logicDeleteSql = getLogicDeleteSql(tableInfo);
			
			RemexSqlMethod sqlMethod = RemexSqlMethod.LOGIC_DELETE_BY_KEY;
			String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo), logicDeleteSql);
			SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
			
			return addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
		} else {  //非逻辑处理
			RemexSqlMethod sqlMethod = RemexSqlMethod.DELETE_BY_KEY;
			String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName());
			SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
			
			return addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
		}
	}
}
