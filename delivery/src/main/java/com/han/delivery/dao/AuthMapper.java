package com.han.delivery.dao;

import org.apache.ibatis.annotations.Mapper;

import com.han.delivery.config.auth.CustomUserDetails;
import com.han.delivery.dto.SignupDto;

@Mapper
public interface AuthMapper {
	
	public int usernameChk(String username);
	
	public void signup(SignupDto signupDto);
	
	public CustomUserDetails getUser(String username);
	
	public int storecount();
}
