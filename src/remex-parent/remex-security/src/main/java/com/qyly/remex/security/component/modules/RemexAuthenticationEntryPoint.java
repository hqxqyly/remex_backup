package com.qyly.remex.security.component.modules;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.security.custom.RemexSecurityCustom;

public class RemexAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setCharacterEncoding(BConst.CHARSET_UTF8);
		response.setContentType(BConst.HTTP_CONTENT_TYPE_JSON);
		response.getWriter().write(RemexSecurityCustom.UN_LOGIN_JSON);
	}
}
