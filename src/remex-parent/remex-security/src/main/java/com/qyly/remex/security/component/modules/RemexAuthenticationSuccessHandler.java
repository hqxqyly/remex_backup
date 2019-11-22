package com.qyly.remex.security.component.modules;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.security.custom.RemexSecurityCustom;

public class RemexAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setCharacterEncoding(BConst.CHARSET_UTF8);
		response.setContentType(BConst.HTTP_CONTENT_TYPE_JSON);
		response.getWriter().write(RemexSecurityCustom.LOGIN_SUCCESS_JSON);
	}
}
