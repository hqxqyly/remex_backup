package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.qyly.remex.mybatis.plus.constant.RemexSqlMethod;

/**
 * mybatis plus全局公共方法 - 根据某字段值修改数据
 * 
 * @author Qiaoxin.Hong
 *
 */
public class UpdateByKey extends BaseMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		RemexSqlMethod sqlMethod = RemexSqlMethod.UPDATE_BY_KEY;
        final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(false, false, tableInfo, false, ENTITY, ENTITY_DOT), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
	}
}
