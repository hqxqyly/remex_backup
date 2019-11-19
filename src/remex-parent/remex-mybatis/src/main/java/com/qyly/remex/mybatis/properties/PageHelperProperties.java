package com.qyly.remex.mybatis.properties;

/**
 * PageHelper properties
 * 
 * @author Qiaoxin.Hong
 *
 */
public class PageHelperProperties {
	
	/** PageHelper properties配置文件前缀 */
	public static final String PAGEHELPER_PREFIX = "pagehelper";
	
	/** 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用 */
	protected String offsetAsPageNum = "true";
	
	/** 设置为true时，使用RowBounds分页会进行count查询 */
	protected String rowBoundsWithCount = "true";
	
	/** 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果 */
	protected String pageSizeZero = "true";
	
	/** 分页参数合理化，启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页， 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据 */
	protected String reasonable = "true";

	public String getOffsetAsPageNum() {
		return offsetAsPageNum;
	}

	public void setOffsetAsPageNum(String offsetAsPageNum) {
		this.offsetAsPageNum = offsetAsPageNum;
	}

	public String getRowBoundsWithCount() {
		return rowBoundsWithCount;
	}

	public void setRowBoundsWithCount(String rowBoundsWithCount) {
		this.rowBoundsWithCount = rowBoundsWithCount;
	}

	public String getPageSizeZero() {
		return pageSizeZero;
	}

	public void setPageSizeZero(String pageSizeZero) {
		this.pageSizeZero = pageSizeZero;
	}

	public String getReasonable() {
		return reasonable;
	}

	public void setReasonable(String reasonable) {
		this.reasonable = reasonable;
	}
}
