package com.han.delivery.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.han.delivery.config.auth.CustomUserDetails;
import com.han.delivery.dto.PointDto;
import com.han.delivery.dto.ReviewDto;
import com.han.delivery.service.UserService;

@Controller
public class MessageController {
	
	
	@MessageMapping("/order-complete-message/{storeId}")
	@SendTo("/topic/order-complete/{storeId}")
	public String message(@DestinationVariable long storeId, String message) {
		System.out.println("가게번호 : " + storeId);
		System.out.println("메세지 도착 :" + message);
		return message;
	}
 
}