package com.qyly.remex.mybatis.plus.injector.methods;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * <pre>
 * mybatis plus全局公共方法 - 重写 - 根据 whereEntity 条件，更新记录
 * 由于原有update方法，没有加入逻辑删除字段的更新，为适用本架构需求，加入逻辑删除字段的更新
 * </pre>
 * 
 * @see com.baomidou.mybatisplus.core.injector.methods.UpdateById
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexUpdate extends AbstractMethod {

	@Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(false, true, tableInfo, true, ENTITY, ENTITY_DOT),
            sqlWhereEntityWrapper(true, tableInfo),
            sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
