package com.qyly.remex.shiro.component.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.qyly.remex.shiro.constant.ShiroOptions;

/**
 * 自定义AuthenticationFilter，未登录授权时返回json格式
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ShiroAuthenticationFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		
		//是否登录过
        boolean isAuthenticated = subject.isAuthenticated();
        
        //登录过，进行权限验证
        if (isAuthenticated) {
        	//开发模式或不开启权限验证
        	if (ShiroOptions.DEV) {
        		return true;
        	}
        	if (!ShiroOptions.DISABLED_PERMIT) {
        		return true;
        	}
        	
        	HttpServletRequest httpServletRequest = (javax.servlet.http.HttpServletRequest) request;
        	//调用地址
        	String callUrl = httpServletRequest.getRequestURI();
        	//权限验证
        	boolean isPermitted = subject.isPermitted(callUrl);
        	if (isPermitted) {
        		return true;
        	} else {
        		unpermitResult(request, response);
        		return false;
			}
        } else {  //未登录
        	unauthResult(request, response);
        	return false;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}
	
	/**
	 * 未登录结果
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void unauthResult(ServletRequest request, ServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(ShiroOptions.UNAUTHORIZED_MSG);
	}
	
	/**
	 * 无权限访问结果
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void unpermitResult(ServletRequest request, ServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(ShiroOptions.UNPERMIT_MSG);
	}
}
