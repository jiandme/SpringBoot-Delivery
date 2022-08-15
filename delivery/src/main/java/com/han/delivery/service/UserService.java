package com.han.delivery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.han.delivery.dao.StoreMapper;
import com.han.delivery.dao.UserMapper;
import com.han.delivery.dto.FoodDto;
import com.han.delivery.dto.FoodOptionDto;
import com.han.delivery.dto.PointDto;
import com.han.delivery.dto.ReviewDto;
import com.han.delivery.dto.StoreDetailDto;
import com.han.delivery.dto.StoreDto;
import com.han.delivery.utils.FileUpload;
import com.han.delivery.utils.Page;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	JavaMailSender mailSender;

	public List<PointDto> myPoint(long id) {
		return userMapper.myPoint(id);
	}

	
	public List<ReviewDto> myReviewList(long id) {
		return userMapper.myReviewList(id);
	}
	 
	 
	public void deleteReview(long id, String orderNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", id);
		map.put("orderNum", orderNum);
		userMapper.deleteReview(map);
	}
	

	public void modifyInfo(String username, String valueType, String value) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("username", username);
	    map.put("valueType", valueType);
	    map.put("value", value);
	    userMapper.modifyInfo(map);
	}
	
	
}
