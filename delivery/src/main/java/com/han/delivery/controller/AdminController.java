package com.han.delivery.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.han.delivery.aop.IsMyStore;
import com.han.delivery.config.auth.CustomUserDetails;
import com.han.delivery.dto.PointDto;
import com.han.delivery.dto.ReviewDto;
import com.han.delivery.dto.StoreDetailDto;
import com.han.delivery.dto.StoreDto;
import com.han.delivery.service.AdminService;
import com.han.delivery.service.StoreService;
import com.han.delivery.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/admin/myStore") 
	public String myStore(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long userId = principal.getId(); 
		List<StoreDto> storeList = adminService.myStore(userId);
		
		model.addAttribute("storeList", storeList);
		return "admin/myStore";
	}
	
	@IsMyStore
	@GetMapping("/admin/management/{id}/detail") 
	public String detail(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails principal, Model model) {
		long userId = principal.getId(); 
		StoreDetailDto storeDetailDto = storeService.storeDetail(id, userId);
		model.addAttribute("store", storeDetailDto);
		model.addAttribute("adminPage", true);
		
		return "admin/detail";
	}
	
	@IsMyStore
	@GetMapping("/admin/management/order/{id}")
	public String order(@PathVariable long id) {
		return "admin/order";
	}
	
	@IsMyStore
	@GetMapping("/admin/management/sales/{id}")
	public String sales(@PathVariable long id) {
		return "admin/sales";
	}
 
}

