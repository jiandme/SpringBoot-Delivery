package com.han.delivery.utils;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.han.delivery.config.auth.CustomUserDetails;

public class UserInfoSessionUpdate {
	 
	public static void sessionUpdate(String value, String valueType, CustomUserDetails principal, HttpSession session) {
 
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(valueType.equals("nickname")) {
			customUserDetails.setNickname(value);
		} 
		else  if(valueType.equals("password")) {
			customUserDetails.setPassword(value);
		}
		else if(valueType.equals("point")) {
			int point = customUserDetails.getPoint() + Integer.parseInt(value);
			customUserDetails.setPoint(point);
		}
		
		else if(valueType.equals("phone")) {
			customUserDetails.setPhone(value);
		}
 
		SecurityContext sc = SecurityContextHolder.getContext();
 
		sc.setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetails, null, principal.getAuthorities()));
		
		session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
	}
}
