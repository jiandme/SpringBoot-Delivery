package com.han.delivery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.han.delivery.dto.FoodDto;
import com.han.delivery.dto.FoodOptionDto;
import com.han.delivery.dto.PointDto;
import com.han.delivery.dto.ReviewDto;
import com.han.delivery.dto.StoreDto;

@Mapper
public interface FindMapper {
	
	
	//유저Id 찾기
	public List<String> findId(String email);
	
	//패스워드 찾기 이메일 일치 확인
	public String emailCheck(Map<String, Object> map);
	 
	//패스워드 찾기 폰번호 일치 확인
	public String phoneCheck(Map<String, Object> map);
	
}
