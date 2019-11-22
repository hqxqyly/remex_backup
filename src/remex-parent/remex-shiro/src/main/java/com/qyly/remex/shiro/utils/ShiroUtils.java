package com.qyly.remex.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.qyly.remex.shiro.bean.LoginResult;
import com.qyly.remex.shiro.constant.ShiroOptions;

/**
 * shiro工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ShiroUtils {

	/**
	 * 登录
	 * @param username 用户名
	 * @param password 密码
	 */
	public static <T> LoginResult<T> login(String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		
		LoginResult<T> loginResult = new LoginResult<>();
		loginResult.setUser(getUser());
		loginResult.setSessionId(getSessionId());
		
		return loginResult;
	}
	
	/**
	 * 取得已登录的用户
	 * @param <T>
	 * @return
	 */
	public static <T> T getUser() {
		Subject subject = SecurityUtils.getSubject();
		@SuppressWarnings("unchecked")
		T user = (T) subject.getPrincipal();
		return user;
	}
	
	/**
	 * 取得sessionId
	 * @return
	 */
	public static String getSessionId() {
		Subject subject = SecurityUtils.getSubject();
		String sessionId = subject.getSession().getId().toString();
		return sessionId;
	}
	
	/**
	 * 注销
	 */
	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
	
	/**
	 * 默认密码盐值算法
	 * @param username 用户名
	 * @return
	 */
	public static ByteSource getByteSource(String username) {
		return ByteSource.Util.bytes(username);
	}
	
	/**
	 * 默认密码盐值算法
	 * @param username 用户名
	 * @return
	 */
	public static String getSalt(String username) {
		return ByteSource.Util.bytes(username).toString();
	}
	
	/**
	 * 默认密码加密算法
	 * @param password 密码
	 * @param salt 密码盐值
	 * @return
	 */
	public static String getEncrypt(String password, String salt) {
		return new SimpleHash(ShiroOptions.ENCRYPT_ALGORITHM_NAME, password, salt, ShiroOptions.ENCRYPT_HASH_ITERATIONS).toHex();
	}
	
	/**
	 * 默认密码加密算法
	 * @param password 密码
	 * @param salt 密码盐值
	 * @return
	 */
	public static String getEncrypt(String password, ByteSource salt) {
		return new SimpleHash(ShiroOptions.ENCRYPT_ALGORITHM_NAME, password, salt, ShiroOptions.ENCRYPT_HASH_ITERATIONS).toHex();
	}
	
	/**
	 * 常用密码加密，以username生成密钥，再生成加密密码
	 * @param password
	 * @param username
	 * @return
	 */
	public static String encryptPassword(String username, String password) {
		ByteSource byteSource = getByteSource(username);
		return encryptPassword(byteSource, password);
	}
	
	/**
	 * 常用密码加密，以username生成密钥，再生成加密密码
	 * @param password
	 * @param username
	 * @return
	 */
	public static String encryptPassword(ByteSource salt, String password) {
		String pwd = getEncrypt(password, salt);
		return pwd;
	}
}
