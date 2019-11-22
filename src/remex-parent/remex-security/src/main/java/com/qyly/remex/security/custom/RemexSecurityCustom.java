package com.qyly.remex.security.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.qyly.remex.utils.ObjectUtils;
import com.qyly.remex.utils.StringUtils;

/**
 * remex spring security自定义处理
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexSecurityCustom {

	/** 登录url */
	public static String LOGIN_PROCESSING_URL = "/login";
	
	/** username参数名 */
	public static String USERNAME_PARAMETER = "username";
	
	/** password参数名 */
	public static String PASSWORD_PARAMETER = "password";
	
	/** 未登录结果json */
	public static String UN_LOGIN_JSON = "{\"code\":\"FAILED\",\"msg\":\"未登录\"}";
	
	/** 登录成功json */
	public static String LOGIN_SUCCESS_JSON = "{\"code\":\"SUCCESS\",\"msg\":\"登录成功\"}";
	
	/** 登录失败json */
	public static String LOGIN_FAILURE_JSON = "{\"code\":\"FAILED\",\"msg\":\"登录失败\"}";
	
	/** 登出成功json */
	public static String LOGOUT_SUCCESS_JSON = "{\"code\":\"SUCCESS\",\"msg\":\"登出成功\"}";
	
	/** 不需要身份认证的url */
	public static List<String> AUTHORIZE_PERMIT_URL = new ArrayList<>();
	
	/** 用户获取业务方法 */
	public static UserDetailsService USER_DETAILS_SERVICE = null;
	
	/** 密码加密方式 */
	public static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	/** token失效时间 */
	public static int TOKEN_VALIDITY_SECONDS = 60 * 30;
	
	/**
	 * 添加不需要身份认证的url
	 * @param url
	 */
	public static void addAuthorizePermitUrl(String...url) {
		if (ObjectUtils.isNotEmpty(url)) {
			
			if (AUTHORIZE_PERMIT_URL == null) {
				AUTHORIZE_PERMIT_URL = new ArrayList<>();
			}
			
			for (String item : url) {
				AUTHORIZE_PERMIT_URL.add(item);
			}
		}
	}
	
	/**
	 * 密码加密
	 * @param password
	 * @return
	 */
	public static String encode(CharSequence password) {
		if (PASSWORD_ENCODER == null) {
			return StringUtils.defaultString(password);
		}
		
		return PASSWORD_ENCODER.encode(password);
	}
}
