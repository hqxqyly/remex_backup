package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * <pre>
 * mybatis plus全局公共方法 - 重写 - 根据ID更新有值字段
 * 由于原有update方法，没有加入逻辑删除字段的更新，为适用本架构需求，加入逻辑删除字段的更新
 * </pre>
 * 
 * @see com.baomidou.mybatisplus.core.injector.methods.UpdateById
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexUpdateById extends AbstractMethod {

	@Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(false, false, tableInfo, false, ENTITY, ENTITY_DOT),
            tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
