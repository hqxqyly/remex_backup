package com.qyly.remex.mybatis.plus.constant;

/**
 * mybatis sql方法
 * 
 * @see com.baomidou.mybatisplus.core.enums.SqlMethod
 * 
 * @author Qiaoxin.Hong
 *
 */
public enum RemexSqlMethod {
	
	SELECT_BY_KEY("selectByKey", "根据某字段值查询数据列表", "<script>\n SELECT %s FROM %s WHERE ${key} = #{value} %s \n</script>"),
	
	SELECT_BY_KEY_ASC("selectByKeyAsc", "根据某字段值查询数据列表并升序", "<script>\n SELECT %s FROM %s WHERE ${key} = #{value} %s order by ${order} asc \n</script>"),
	
	SELECT_BY_KEY_DESC("selectByKeyDesc", "根据某字段值查询数据列表并降序", "<script>\n SELECT %s FROM %s WHERE ${key} = #{value} %s order by ${order} desc \n</script>"),
	
	SELECT_ONE_BY_KEY("selectOneByKey", "根据某字段值查询单条数据", "<script>\n SELECT %s FROM %s WHERE ${key} = #{value} %s \n</script>"),
	
	DELETE_BY_KEY("deleteByKey", "根据某字段值删除数据", "<script>\n DELETE FROM %s WHERE ${key} = #{value} \n</script>"),
	LOGIC_DELETE_BY_KEY("deleteByKey", "根据某字段值逻辑删除数据", "<script>\n UPDATE %s %s WHERE ${key} = #{value} %s \n</script>"),

	UPDATE_BY_KEY("updateByKey", "根据某字段值修改数据", "<script>\n UPDATE %s %s WHERE ${key} = #{value} %s \n</script>"),
	
	SELECT_BY_ENTITY("selectByEntity", "根据条件查询数据列表", "<script>\n SELECT %s FROM %s %s \n</script>"),
	
	SELECT_PAGE_BY_ENTITY("selectPageByEntity", "根据条件查询数据列表", "<script>\n SELECT %s FROM %s %s \n</script>"),
	;
	
	private final String method;
    private final String desc;
    private final String sql;

    RemexSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
