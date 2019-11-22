package com.qyly.remex.security.component.modules;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.security.custom.RemexSecurityCustom;

public class RemexAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setCharacterEncoding(BConst.CHARSET_UTF8);
		response.setContentType(BConst.HTTP_CONTENT_TYPE_JSON);
		response.getWriter().write(RemexSecurityCustom.LOGIN_FAILURE_JSON);
	}
}
