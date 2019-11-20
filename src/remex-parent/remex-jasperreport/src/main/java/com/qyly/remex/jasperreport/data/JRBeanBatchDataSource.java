package com.qyly.remex.jasperreport.data;

import java.util.ArrayList;
import java.util.List;

import com.qyly.remex.jasperreport.interfaces.IReportBatchReadHandle;
import com.qyly.remex.utils.CollectionUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

/**
 * jasperreport所用，支持分批提取数据的数据源
 * 
 * @author Qiaoxin.Hong
 *
 */
public class JRBeanBatchDataSource extends JRAbstractBeanDataSource {
	
	/** 数据列表 */
	private List<?> dataList = new ArrayList<>();
	
	/** 当前读取的页码 */
	private int curPageIndex = 0;
	
	/** 当前数据下标 */
	private int curIndex = -1;
	
	/** 数据读取器 */
	private IReportBatchReadHandle readHandle;

	public JRBeanBatchDataSource(boolean isUseFieldDescription) {
		super(isUseFieldDescription);
	}
	
	public JRBeanBatchDataSource(IReportBatchReadHandle readHandle) {
		super(true);
		this.readHandle = readHandle;
	}



	@Override
	public boolean next() throws JRException {
		//第一页，或当前页数据已解析完了，则读取下一页
		if (curIndex == -1 || curIndex >= dataList.size() - 1) {
			curPageIndex = curPageIndex + 1;
			curIndex = -1;
			
			//读取数据
			dataList = CollectionUtils.defaultList(readHandle.read(curPageIndex));
		}
		
		//当没有数据了，则解析完毕
		if (dataList.size() == 0) {
			return false;
		}

		//将读取当前数据下标的索引移到下一位
		curIndex = curIndex + 1;

		return true;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		//当前对象
		Object curBean = dataList.get(curIndex);
		
		return getFieldValue(curBean, field);
	}

	@Override
	public void moveFirst() throws JRException {
		//???????
	}
}
