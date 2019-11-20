package com.qyly.remex.jasperreport.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.qyly.remex.exception.RemexException;
import com.qyly.remex.jasperreport.data.JRBeanBatchDataSource;
import com.qyly.remex.jasperreport.interfaces.IReportBatchReadHandle;
import com.qyly.remex.utils.Assert;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * 导出工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ExportUtils {
	
	/**
	 * 导出excel，分批读取数据，避免数据过大，一次性加载对内存造成过大压力
	 * @param outputStream 文件输出流
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param readHandle 分批提取数据的数据读取器
	 */
	public static void exportExcel(OutputStream outputStream, InputStream templateInputStream, Map<String, Object> parameters
			, IReportBatchReadHandle readHandle) {
		JRBeanBatchDataSource dataSource = new JRBeanBatchDataSource(readHandle);
		exportExcel(outputStream, templateInputStream, parameters, dataSource);
	}
	
	/**
	 * 导出excel，一次性写入数据列表的数据
	 * @param outputStream 文件输出流
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param dataList 数据列表
	 */
	public static void exportExcel(OutputStream outputStream, InputStream templateInputStream, Map<String, Object> parameters
			, Collection<?> dataList) {
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
		exportExcel(outputStream, templateInputStream, parameters, dataSource);
	}
	
	/**
	 * 导出excel
	 * @param outputStream 文件输出流
	 * @param templateInputStream 模板文件流
	 * @param parameters 参数
	 * @param dataSource 数据源
	 */
	public static void exportExcel(OutputStream outputStream, InputStream templateInputStream, Map<String, Object> parameters
			, JRDataSource dataSource) {
		try {
			JasperReport jasperReport = createJasperReport(templateInputStream);
			exportExcel(outputStream, jasperReport, parameters, dataSource);
		} catch (Exception e) {
			throw new RemexException("Jasperreport export excel error", e);
		}
	}

	/**
	 * 导出excel
	 * @param outputStream 文件输出流
	 * @param jasperReport 模板信息
	 * @param parameters 参数
	 * @param dataSource 数据源
	 */
	public static void exportExcel(OutputStream outputStream, JasperReport jasperReport, Map<String, Object> parameters
			, JRDataSource dataSource) {
		Assert.notNull(outputStream, "Report export outputStream must not be null");
		Assert.notNull(jasperReport, "Report export templateInputStream must not be null");
		Assert.notNull(dataSource, "Report export dataSource must not be null");
		
		try {
			if (parameters == null) {
				parameters = new HashMap<>();
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			
			JRXlsxExporter exporter = new JRXlsxExporter();
			
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			
			exporter.exportReport();
		} catch (Exception e) {
			throw new RemexException("Jasperreport export excel error", e);
		}
	}
	
	/**
	 * 以模板文件流创建模板信息
	 * @param templateInputStream 模板文件流
	 * @return
	 */
	public static JasperReport createJasperReport(InputStream templateInputStream) {
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(templateInputStream);
			return jasperReport;
		} catch (Exception e) {
			throw new RemexException("Jasperreport export excel error", e);
		}
	}
}
