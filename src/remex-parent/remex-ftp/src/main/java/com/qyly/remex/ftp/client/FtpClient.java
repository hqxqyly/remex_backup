package com.qyly.remex.ftp.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.ftp.properties.FtpProperties;
import com.qyly.remex.utils.Assert;
import com.qyly.remex.utils.Assist;
import com.qyly.remex.utils.FileUtils;
import com.qyly.remex.utils.IdUtils;
import com.qyly.remex.utils.StringUtils;

/**
 * ftp管理类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class FtpClient {
protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** ftp服务器地址 */
	protected String hostName = null;
    
	/** ftp服务器端口 */
	protected Integer port = null;
    
    /** ftp服务器用户名 */
	protected String userName = null;
    
    /** ftp服务器密码 */
	protected String password = null;
    
    /** ftp每次读取文件流时缓存数组的大小，默认1024 */
	protected int bufferSize = 1024;
	
	public FtpClient() {
		
	}
	
	public FtpClient(FtpProperties properties) {
		//提取ftp配置属性
		fetchFtpProperties(properties);
	}
	
	/**
	 * 上传
	 * @param destDirectory 目标文件路径，为空则保存到根目录下
	 * @param destFileName 目标文件名
	 * @param srcInputStream 源文件输入流
	 */
	public void upload(String destDirectory, String destFileName, InputStream srcInputStream) {
		Assert.notBlank(destFileName, "ftp upload destFileName is blank");
		Assert.notNull(srcInputStream, "ftp upload srcInputStream is null");
		
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
			Assist.close(srcInputStream);
		}
	}
	
	/**
	 * 上传
	 * @param destDirectory 目标文件路径，为空则保存到根目录下
	 * @param file 文件
	 */
	public String upload(String destDirectory, MultipartFile file) {
		String fileName = null;
		try {
			InputStream inputStream = file.getInputStream();
			fileName = IdUtils.createId() + FileUtils.getFileSuffix(file.getOriginalFilename());
			upload(destDirectory, fileName, inputStream);
		} catch (Exception e) {
			throw new RemexException("ftp upload error", e);
		}
		return fileName;
	}
	
	
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcDirectory, String srcFileName, OutputStream destOutputStream) {
		//文件完整路径
		String srcFilePath = packFilePath(srcDirectory, srcFileName);
		//下载
		download(srcFilePath, destOutputStream);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcFilePath, OutputStream destOutputStream) {
		Assert.notBlank(srcFilePath, "ftp download srcFilePath is blank");
		Assert.notNull(destOutputStream, "ftp download destOutputStream is null");
		
		FTPClient ftpClient = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			ftpClient.enterLocalPassiveMode();
			// 下载
			boolean flag = ftpClient.retrieveFile(srcFilePath, destOutputStream);
			Assert.isTrue(flag, "ftp download result is false");
		} catch (Exception e) {
			throw new RemexException("ftp download error", e);
		} finally {
			closeFtp(ftpClient);
			Assist.close(destOutputStream);
		}
	}
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcDirectory, String srcFileName, HttpServletResponse response) {
		//文件完整路径
		String srcFilePath = packFilePath(srcDirectory, srcFileName);
		//下载
		download(srcFilePath, response);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public void download(String srcFilePath, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("multipart/form-data;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;fileName=" + srcFilePath);
			
			OutputStream outputStream = response.getOutputStream();
			
			download(srcFilePath, outputStream);
		} catch (Exception e) {
			throw new RemexException("ftp download error", e);
		}
	}
	
	/**
	 * 下载
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 * @param destOutputStream 目标文件输出流
	 */
	public String downloadBase64(String srcDirectory, String srcFileName, OutputStream destOutputStream) {
		//文件完整路径
		String srcFilePath = packFilePath(srcDirectory, srcFileName);
		//下载
		return downloadBase64(srcFilePath, destOutputStream);
	}
	
	/**
	 * 下载
	 * @param srcFilePath 源文件完整路径
	 * @param destOutputStream 目标文件输出流
	 */
	public String downloadBase64(String srcFilePath, OutputStream destOutputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		download(srcFilePath, byteArrayOutputStream);
		byte[] bytes = byteArrayOutputStream.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	/**
	 * 文件删除
	 * @param srcDirectory 源文件路径
	 * @param srcFileName 源文件名
	 */
	public void deleteFile(String srcDirectory, String srcFileName) {
		//文件完整路径
		String srcFilePath = packFilePath(srcDirectory, srcFileName);
		//文件删除
		deleteFile(srcFilePath);
	}
	
	/**
	 * 文件删除
	 * @param srcFilePath 源文件完整路径
	 */
	public void deleteFile(String srcFilePath) {
		Assert.notBlank(srcFilePath, "ftp deleteFile srcFilePath is blank");
		
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
		Assert.notBlank(srcFilePath, "ftp copyFile srcFilePath is blank");
		Assert.notBlank(destDirectory, "ftp copyFile destDirectory is blank");
		Assert.notBlank(destFileName, "ftp copyFile destFileName is blank");
		
		FTPClient ftpClient = null;
		InputStream inputStream = null;
		try {
			//取得ftp
			ftpClient = getFtp();
			
			//封装目录路径
			srcFilePath = packFileDirectory(srcFilePath);
			
			//取得源文件输入流
			inputStream = ftpClient.retrieveFileStream(srcFilePath);
			Assert.notNull(inputStream, "ftp copyFile inputStream is null");

			//上传文件
			upload(destDirectory, destFileName, inputStream);
			
		} catch (Exception e) {
			throw new RemexException("ftp copyFile error", e);
		} finally {
			closeFtp(ftpClient);
			Assist.close(inputStream);
		}
	}
	
	
	/**
	 * 改变目录路径
	 * @param ftpClient ftpClient
	 * @param fileDirectory 文件路径
	 */
	protected void changeWorkingDirectory(FTPClient ftpClient, String fileDirectory) {
		Assert.notNull(ftpClient, "ftpClient is null");
		
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
	 * 提取ftp配置属性
	 * @param properties
	 */
	public void fetchFtpProperties(FtpProperties properties) {
		this.hostName = properties.getHostName();
		this.port = properties.getPort();
		this.userName = properties.getUserName();
		this.password = properties.getPassword();
		this.bufferSize = properties.getBufferSize();
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
			ftpClient.connect(hostName, port);
			//登录FTP服务器
			ftpClient.login(userName, password);
			//以二进制上传文件
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);  
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//每次读取文件流时缓存数组的大小
			ftpClient.setBufferSize(bufferSize);
			
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
	
	/**
	 * 封装文件路径
	 * @param fileDirectory
	 * @param fileName
	 * @return
	 */
	protected String packFilePath(String fileDirectory, String fileName) {
		Assert.notBlank(fileName, "ftp fileName is blank");

		//封装目录路径
		fileDirectory = packFileDirectory(fileDirectory);

		//文件完整路径
		String srcFilePath = FileUtils.joinFilePath(fileDirectory, fileName);
		
		return srcFilePath;
	}
	

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
}
