package com.qyly.remex.shiro.component.access;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.qyly.remex.shiro.constant.ShiroOptions;

/**
 * shiro session manager，从Headers中取得sessionId
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

	@Override
	protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
		//重写sessionId的获取方式
		HttpServletRequest request =  (HttpServletRequest) servletRequest;
		String token = request.getHeader(ShiroOptions.SESSION_ID_FIELD_NAME);
		return token;
	}
}
