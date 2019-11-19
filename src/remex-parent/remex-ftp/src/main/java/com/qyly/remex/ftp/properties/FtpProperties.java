package com.qyly.remex.ftp.properties;

/**
 * ftp properties
 * 
 * @author Qiaoxin.Hong
 *
 */
public class FtpProperties {
	
	/** ftp properties配置文件前缀 */
	public static final String FTP_PREFIX = "ftp";
	
	/** ftp服务器地址 */
	private String hostName = "localhost";
    
	/** ftp服务器端口 */
    private Integer port = 21;
    
    /** ftp服务器用户名 */
    private String userName = null;
    
    /** ftp服务器密码 */
    private String password = null;
    
    /** ftp每次读取文件流时缓存数组的大小，默认1024 */
    private int bufferSize = 1024;

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
