package com.qyly.remex.mybatis.plus.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.qyly.remex.constant.BConst;

/**
 * mybatis plus全局公共方法基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseMethod extends AbstractMethod {

	/**
	 * 取得逻辑删除的拼接sql
	 * @param tableInfo
	 * @return
	 */
	protected String getLogicDeleteSql(TableInfo tableInfo) {
		String logicDeleteSql;

		//逻辑删除
		if (tableInfo.isLogicDelete()) {
			logicDeleteSql = tableInfo.getLogicDeleteSql(true, false);
		} else {
			logicDeleteSql = BConst.EMPTY;
		}
		
		return logicDeleteSql;
	}
	
	/**
	 * 拼接实体的where条件
	 * @param table
	 * @return
	 */
	protected String sqlWhereEntity(TableInfo table) {
		//取得where条件
		String sql = table.getAllSqlWhere(true, true, ENTITY_DOT);
		sql += (NEWLINE + table.getLogicDeleteSql(true, false) + NEWLINE);
		sql = SqlScriptUtils.convertWhere(sql);
		return sql;
	}
}
