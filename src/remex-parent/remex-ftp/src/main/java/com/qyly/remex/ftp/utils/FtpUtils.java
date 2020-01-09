package com.qyly.remex.ftp.utils;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.qyly.remex.ftp.client.FtpClient;
import com.qyly.remex.utils.ApplicationContextUtils;
import com.qyly.remex.utils.Assist;

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
		ApplicationContext applicationContext = Assist.notNull(ApplicationContextUtils.getContext()
				, "applicationContext cannot be null");
		return Assist.notNull(applicationContext.getBean(FtpClient.class), "ftpClient is null");
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
	 * 上传
	 * @param destDirectory 目标文件路径，为空则保存到根目录下
	 * @param file 文件
	 */
	public static String upload(String destDirectory, MultipartFile file) {
		return getFtpClient().upload(destDirectory, file);
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
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param response
	 */
	public static void download(String srcDirectory, String srcFileName, HttpServletResponse response) {
		getFtpClient().download(srcDirectory, srcFileName, response);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param response
	 */
	public static void download(String srcFilePath, HttpServletResponse response) {
		getFtpClient().download(srcFilePath, response);
	}
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public static String downloadBase64(String srcDirectory, String srcFileName, OutputStream destOutputStream) {
		return getFtpClient().downloadBase64(srcDirectory, srcFileName, destOutputStream);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public static String downloadBase64(String srcFilePath, OutputStream destOutputStream) {
		return getFtpClient().downloadBase64(srcFilePath, destOutputStream);
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
