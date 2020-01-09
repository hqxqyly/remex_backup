package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.qyly.remex.mybatis.plus.constant.RemexSqlMethod;

/**
 * mybatis plus全局公共方法 - 根据某字段值查询数据列表
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SelectByKey extends BaseMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		RemexSqlMethod sqlMethod = RemexSqlMethod.SELECT_BY_KEY;
		//取得逻辑删除的拼接sql
		String logicDeleteSql = getLogicDeleteSql(tableInfo);
		
		String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(tableInfo, false), tableInfo.getTableName(), logicDeleteSql);
		
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
		return addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
	}
}
