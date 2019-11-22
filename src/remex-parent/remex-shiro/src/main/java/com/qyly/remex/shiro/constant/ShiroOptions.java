package com.qyly.remex.shiro.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.qyly.remex.shiro.component.modules.BaseShiroRealm;

/**
 * shiro配置项
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ShiroOptions {
	
	/** 是否启用开发模式 */
	public static boolean DEV = false;
	
	/** 是否开启权限验证 */
	public static boolean DISABLED_PERMIT = false;
	
	/** shiro session id在servletRequest header中的字段名 */
	public static String SESSION_ID_FIELD_NAME = "token";
	
	/** 加密算法方式 */
	public static String ENCRYPT_ALGORITHM_NAME = Md5Hash.ALGORITHM_NAME;
	
	/** 加密次数 */
	public static int ENCRYPT_HASH_ITERATIONS = 1024;
	
	/** AuthorizingRealm Class */
	public static Class<? extends AuthorizingRealm> AUTH_REALM_CLASS = BaseShiroRealm.class;
	
	/** SessionManager Class */
	public static Class<? extends DefaultSessionManager> SESSION_MANAGER_CLASS = DefaultWebSessionManager.class;
	
	/** 权限映射 */
	public static Map<String, String> FILTER_CHAIN_DEFINITION_MAP = new LinkedHashMap<>();
	
	/** 未登录消息，json格式 */
	public static String UNAUTHORIZED_MSG = "{}";
	
	/** 无权限访问消息，json格式 */
	public static String UNPERMIT_MSG = "{}";
}
