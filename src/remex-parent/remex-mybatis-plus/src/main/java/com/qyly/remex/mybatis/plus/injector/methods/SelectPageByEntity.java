package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.qyly.remex.mybatis.plus.constant.RemexSqlMethod;

/**
 * <pre>
 * mybatis plus全局公共方法 - 根据条件分页查询数据列表
 * 从实体中获取属性值进行 key = value 的条件筛选
 * </pre>
 * 
 * @author Qiaoxin.Hong
 *
 */
public class SelectPageByEntity extends BaseMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		RemexSqlMethod sqlMethod = RemexSqlMethod.SELECT_PAGE_BY_ENTITY;
		
		String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(tableInfo, false), tableInfo.getTableName()
				, sqlWhereEntity(tableInfo));

		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
	}
}
