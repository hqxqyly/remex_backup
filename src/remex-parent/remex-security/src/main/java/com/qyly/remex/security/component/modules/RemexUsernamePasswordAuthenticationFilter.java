package com.qyly.remex.security.component.modules;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qyly.remex.exception.RemexException;

public class RemexUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
				|| MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				
				InputStream is = request.getInputStream();
				@SuppressWarnings("unchecked")
				Map<String,String> authenticationBean = mapper.readValue(is, Map.class);
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get(getUsernameParameter()), authenticationBean.get(getPasswordParameter()));
				
				setDetails(request, authRequest);

				return this.getAuthenticationManager().authenticate(authRequest);
			} catch (AuthenticationException e) {
				throw e;
			} catch (Exception e) {
				throw new RemexException("remex spring security attempt authentication error", e);
			}
		}
		
		return super.attemptAuthentication(request, response);
	}
}
