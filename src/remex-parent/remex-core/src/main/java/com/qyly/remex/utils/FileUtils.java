package com.qyly.remex.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.exception.RemexException;

/**
 * 文件工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class FileUtils {
	
	/**
	 * 创建FileInputStream
	 * @param file
	 * @return
	 */
	public static FileInputStream createFileInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RemexException("create FileInputStream error", e);
		}
	}
	
	/**
	 * 拼接路径，以"/"进行拼接
	 * @param paths
	 * @return
	 */
	public static String joinFilePath(String...paths) {
		StringBuilder sb = null;
		for (String path : paths) {
			if (StringUtils.isBlank(path)) {
				throw new RemexException("file path is blank!");
			}
			
			if (sb == null) {
				sb = new StringBuilder();
			} else if (sb.lastIndexOf(BConst.SLASH_LEFT) != sb.length() - 1 ) {
				sb.append(BConst.SLASH_LEFT);
			}
			sb.append(path);
		}
		
		return sb.toString();
	}
	
	/**
	 * 取得文件后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		if (fileName != null && fileName.lastIndexOf(BConst.PERIOD) != -1) {
			return BConst.PERIOD + fileName.substring(fileName.lastIndexOf(BConst.PERIOD) + 1);
		}
		return BConst.EMPTY;
	}
}
