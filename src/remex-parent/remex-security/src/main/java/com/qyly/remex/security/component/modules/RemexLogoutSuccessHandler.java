package com.qyly.remex.security.component.modules;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.qyly.remex.constant.BConst;
import com.qyly.remex.security.custom.RemexSecurityCustom;

public class RemexLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setCharacterEncoding(BConst.CHARSET_UTF8);
		response.setContentType(BConst.HTTP_CONTENT_TYPE_JSON);
		response.getWriter().write(RemexSecurityCustom.LOGOUT_SUCCESS_JSON);
	}
}
