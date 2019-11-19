package com.qyly.remex.ftp.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.ftp.properties.FtpProperties;
import com.qyly.remex.utils.Assert;
import com.qyly.remex.utils.FileUtils;
import com.qyly.remex.utils.ObjectUtils;
import com.qyly.remex.utils.StringUtils;

/**
 * ftp管理类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class FtpClient {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 配置属性 */
	protected FtpProperties context = new FtpProperties();
	
	public FtpClient() {
		
	}
	
	public FtpClient(FtpProperties context) {
		setContext(context);
	}
	
	/**
	 * 上传
	 * @param destDirectory 目标文件路径，为空则保存到根目录下
	 * @param destFileName 目标文件名
	 * @param srcInputStream 源文件输入流
	 */
	public void upload(String destDirectory, String destFileName, InputStream srcInputStream) {
		Assert.notBlank(destFileName, "ftp upload destFileName cannot be blank");
		Assert.notNull(srcInputStream, "ftp upload srcInputStream cannot be null");
		
		FTPClient ftpClient = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			//封装目录路径
			destDirectory = packFileDirectory(destDirectory);
			
			//改变目录路径
			changeWorkingDirectory(ftpClient, destDirectory);
			
			//上传
			boolean flag = ftpClient.storeFile(destFileName, srcInputStream);
			Assert.isTrue(flag, "ftp upload result is false");
		} catch (Exception e) {
			throw new RemexException("ftp upload error", e);
		} finally {
			closeFtp(ftpClient);
			ObjectUtils.close(srcInputStream);
		}
	}
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcDirectory, String srcFileName, OutputStream destOutputStream) {
		Assert.notBlank(srcFileName, "ftp download srcFileName cannot be blank");
		
		//封装目录路径
		srcDirectory = packFileDirectory(srcDirectory);

		//文件完整路径
		String srcFilePath = FileUtils.joinFilePath(srcDirectory, srcFileName);
		
		//下载
		download(srcFilePath, destOutputStream);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcFilePath, OutputStream destOutputStream) {
		Assert.notBlank(srcFilePath, "ftp download srcFilePath cannot be blank");
		Assert.notNull(destOutputStream, "ftp download destOutputStream cannot be null");
		
		FTPClient ftpClient = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			//封装目录路径
			srcFilePath = packFileDirectory(srcFilePath);
			
			ftpClient.enterLocalPassiveMode();
			// 下载
			boolean flag = ftpClient.retrieveFile(srcFilePath, destOutputStream);
			Assert.isTrue(flag, "ftp download result is false");
		} catch (Exception e) {
			throw new RemexException("ftp download error", e);
		} finally {
			closeFtp(ftpClient);
			ObjectUtils.close(destOutputStream);
		}
	}
	
	/**
	 * 文件删除
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 */
	public void deleteFile(String srcDirectory, String srcFileName) {
		Assert.notBlank(srcFileName, "ftp download srcFileName cannot be blank");

		//封装目录路径
		srcDirectory = packFileDirectory(srcDirectory);

		//文件完整路径
		String srcFilePath = FileUtils.joinFilePath(srcDirectory, srcFileName);

		//文件删除
		deleteFile(srcFilePath);
	}
	
	/**
	 * 文件删除
	 * @param srcFilePath 源文件完整路径
	 */
	public void deleteFile(String srcFilePath) {
		Assert.notBlank(srcFilePath, "ftp deleteFile srcFilePath cannot be blank");
		
		FTPClient ftpClient = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			//封装目录路径
			srcFilePath = packFileDirectory(srcFilePath);
			
			//下载
			boolean flag = ftpClient.deleteFile(srcFilePath);
			Assert.isTrue(flag, "ftp deleteFile result is false");
		} catch (Exception e) {
			throw new RemexException("ftp deleteFile error", e);
		} finally {
			closeFtp(ftpClient);
		}
	}
	
	/**
	 * 复制文件
	 * @param srcFilePath 源文件完整路径
	 * @param destDirectory 目标文件路径
	 * @param destFileName 目标文件名
	 */
	public void copyFile(String srcFilePath, String destDirectory, String destFileName) {
		Assert.notBlank(srcFilePath, "ftp copyFile srcFilePath cannot be blank");
		Assert.notBlank(destDirectory, "ftp copyFile destDirectory cannot be blank");
		Assert.notBlank(destFileName, "ftp copyFile destFileName cannot be blank");
		
		FTPClient ftpClient = null;
		InputStream inputStream = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			//封装目录路径
			srcFilePath = packFileDirectory(srcFilePath);
			
			//取得源文件输入流
			inputStream = ftpClient.retrieveFileStream(srcFilePath);
			Assert.notNull(inputStream, "ftp copyFile inputStream cannot be null");

			//上传文件
			upload(destDirectory, destFileName, inputStream);
			
		} catch (Exception e) {
			throw new RemexException("ftp copyFile error", e);
		} finally {
			closeFtp(ftpClient);
			ObjectUtils.close(inputStream);
		}
	}
	
	
	/**
	 * 改变目录路径
	 * @param ftpClient ftpClient
	 * @param fileDirectory 文件路径
	 */
	protected void changeWorkingDirectory(FTPClient ftpClient, String fileDirectory) {
		Assert.notNull(ftpClient, "ftpClient cannot be null");
		
		if (StringUtils.isNotBlank(fileDirectory)) {
			try {
				//重新封装文件路径
				fileDirectory = packFileDirectory(fileDirectory);
				
				// 改变目录路径
				boolean flag = ftpClient.changeWorkingDirectory(fileDirectory);
				// 目录路径不存在
				if (!flag) {
					String[] directoryArr = fileDirectory.split(BConst.SLASH_LEFT);
					for (String directory : directoryArr) {
						directory = directory.trim();
						if (StringUtils.isBlank(directory)) {
							continue;
						}

						// 改变目录路径
						flag = ftpClient.changeWorkingDirectory(directory);
						// 目录路径不存在
						if (!flag) {
							flag = ftpClient.makeDirectory(directory);
							if (!flag) {
								throw new RemexException("ftp make directory error!");
							}

							// 再次改变目录路径
							flag = ftpClient.changeWorkingDirectory(directory);
							// 如果再次失败
							if (!flag) {
								throw new RemexException("ftp change again working directory error!");
							}
						}
					}
				}
			} catch (Exception e) {
				throw new RemexException("ftp change working directory error!", e);
			}
		}
	}
	
	/**
	 * 连接ftp
	 * @return
	 */
	protected FTPClient connectFtp() {
		try {
			FTPClient ftpClient = new FTPClient();
			
			ftpClient.enterLocalPassiveMode();
			//连接FTP服务器
			ftpClient.connect(context.getHostName(), context.getPort());
			//登录FTP服务器
			ftpClient.login(context.getUserName(), context.getPassword());
			//以二进制上传文件
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);  
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//每次读取文件流时缓存数组的大小
			ftpClient.setBufferSize(context.getBufferSize());
			
			return ftpClient;
		} catch (Exception e) {
			throw new RemexException("ftp connect error", e);
		}
	}
	
	/**
	 * 关闭FTPClient
	 * 
	 * @param ftpClient
	 */
	protected void closeFtp(FTPClient ftpClient) {
		if (ftpClient != null) {
			try {
				ftpClient.logout();
			} catch (Exception e) {
				logger.error("ftp close error", e);
			}
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				logger.error("ftp close error", e);
			}
		}
	}
	
	/**
	 * 验证FTP服务器
	 * @param ftpClient
	 */
	protected void validateFtp(FTPClient ftpClient) {
		Assert.notNull(ftpClient, "ftp connect ftpClient is null");

		int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			throw new RemexException("ftp connect error");
		}
	}
	
	/**
	 * 取得可使用的ftpClient
	 */
	protected FTPClient getFtp() {
		//连接ftp
		FTPClient ftpClient = connectFtp();
		// 验证ftp
		validateFtp(ftpClient);
		
		return ftpClient;
	}
	
	/**
	 * 封装目录路径，如果为空则为根路径
	 * @param fileDirectory
	 * @return
	 */
	protected String packFileDirectory(String fileDirectory) {
		if (StringUtils.isEmpty(fileDirectory)) {
			fileDirectory = "/";
		} else {
			if (!fileDirectory.startsWith(BConst.SLASH_LEFT)) {
				fileDirectory = BConst.SLASH_LEFT + fileDirectory;
			}

			if (!fileDirectory.endsWith(BConst.SLASH_LEFT)) {
				fileDirectory = fileDirectory + BConst.SLASH_LEFT;
			}
		}

		return fileDirectory;
	}
	

	public void setContext(FtpProperties context) {
		this.context = context;
	}
	
	public FtpProperties getContext() {
		return context;
	}
}
