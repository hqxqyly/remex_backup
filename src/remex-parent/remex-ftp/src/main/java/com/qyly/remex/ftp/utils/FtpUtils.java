package com.qyly.remex.ftp.utils;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.context.ApplicationContext;

import com.qyly.remex.ftp.client.FtpClient;
import com.qyly.remex.utils.ApplicationContextUtils;
import com.qyly.remex.utils.Assert;

/**
 * ftp工具类
 * 
 * @author Qiaoxin.Hong
 * 
 * @see com.qyly.remex.utils.ApplicationContextUtils
 * @see com.qyly.remex.ftp.client.FtpClient
 * @see com.qyly.remex.ftp.config.FtpConfig
 */
public class FtpUtils {
	
	/**
	 * 取得ftp管理类
	 * @return
	 */
	public static FtpClient getFtpClient() {
		ApplicationContext applicationContext = ApplicationContextUtils.getContext();
		Assert.notNull(applicationContext, "applicationContext cannot be null");
		
		FtpClient ftpClient = applicationContext.getBean(FtpClient.class);
		Assert.notNull(ftpClient, "ftpClient cannot be null");
		
		return ftpClient;
	}
	
	/**
	 * 上传
	 * @param destDirectory 目标文件路径，为空则保存到根目录下
	 * @param destFileName 目标文件名
	 * @param srcInputStream 源文件输入流
	 */
	public static void upload(String destDirectory, String destFileName, InputStream srcInputStream) {
		getFtpClient().upload(destDirectory, destFileName, srcInputStream);
	}
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public static void download(String srcDirectory, String srcFileName, OutputStream destOutputStream) {
		getFtpClient().download(srcDirectory, srcFileName, destOutputStream);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public static void download(String srcFilePath, OutputStream destOutputStream) {
		getFtpClient().download(srcFilePath, destOutputStream);
	}
	
	/**
	 * 文件删除
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 */
	public static void deleteFile(String srcDirectory, String srcFileName) {
		getFtpClient().deleteFile(srcDirectory, srcFileName);
	}
	
	/**
	 * 文件删除
	 * @param srcFilePath 源文件完整路径
	 */
	public static void deleteFile(String srcFilePath) {
		getFtpClient().deleteFile(srcFilePath);
	}
	
	/**
	 * 复制文件
	 * @param srcFilePath 源文件完整路径
	 * @param destDirectory 目标文件路径
	 * @param destFileName 目标文件名
	 */
	public static void copyFile(String srcFilePath, String destDirectory, String destFileName) {
		getFtpClient().copyFile(srcFilePath, destDirectory, destFileName);
	}
}
