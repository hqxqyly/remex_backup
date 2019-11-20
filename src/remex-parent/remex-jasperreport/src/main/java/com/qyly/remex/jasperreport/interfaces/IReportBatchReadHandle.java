 package com.qyly.remex.jasperreport.interfaces;

import java.util.List;

/**
  * 用于分批提取数据的数据读取器
  * 
  * @author Qiaoxin.Hong
  *
  */
public interface IReportBatchReadHandle {

	/**
	 * 读取当前页的数据列表
	 * @param curPageIndex 页码
	 * @return
	 */
	public List<?> read(int curPageIndex);
}
