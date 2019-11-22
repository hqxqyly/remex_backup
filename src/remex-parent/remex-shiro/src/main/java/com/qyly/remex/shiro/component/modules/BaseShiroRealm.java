package com.qyly.remex.shiro.component.modules;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.qyly.remex.shiro.utils.ShiroUtils;
import com.qyly.remex.utils.CollectionUtils;

/**
 * 登录权限登录器基础类
 * 
 * @author Qiaoxin.Hong
 *
 * @param <U> 查询用户的实体
 * @param <P> 保存在shiro中的缓存实体
 */
public abstract class BaseShiroRealm<U, P> extends AuthorizingRealm {

	/**
	 * 认证登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		//查询用户
		U userDto = findUser(upToken.getUsername());
		if (userDto == null) {
			throw new UnknownAccountException("user not exists");
		}
		
		//取得密码
		String password = getUserPassword(userDto);
		
		//密码盐值
		ByteSource byteSource = ShiroUtils.getByteSource(getForSalt(upToken.getUsername()));
		
		//转换保存在shiro中的缓存实体
		P shiroUserInfoDto = convertShiroUserInfo(userDto); 
		
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(shiroUserInfoDto, password, byteSource, getName());
		
		return authenticationInfo;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		@SuppressWarnings("unchecked")
		//取得登录信息
		P shiroUserInfoDto = (P) getAvailablePrincipal(principals);
		//取得权限列表
		List<String> permissions = CollectionUtils.defaultList(getPermissions(shiroUserInfoDto));
		info.addStringPermissions(permissions);
		
		return info;
	}
	
	/**
	 * 查询用户
	 * @param username
	 * @return
	 */
	protected abstract U findUser(String username);
	
	/**
	 * 转换保存在shiro中的缓存实体
	 * @param user
	 * @return
	 */
	protected abstract P convertShiroUserInfo(U user);
	
	/**
	 * 取得用户密码
	 * @return
	 */
	protected abstract String getUserPassword(U user);
	
	/**
	 * 取得权限列表
	 * @param shiroUserInfo 保存在shiro中的缓存实体
	 * @return
	 */
	protected List<String> getPermissions(P shiroUserInfo) {
		return null;
	}
	
	/**
	 * 取得用于密码盐值的字符串
	 * @param username
	 * @return
	 */
	protected String getForSalt(String username) {
		return username;
	}
}
