package com.han.delivery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.han.delivery.config.auth.CustomUserDetails;
import com.han.delivery.dao.AdminMapper;
import com.han.delivery.dao.OrderMapper;
import com.han.delivery.dao.StoreMapper;
import com.han.delivery.dto.CartDto;
import com.han.delivery.dto.CartListDto;
import com.han.delivery.dto.FoodDto;
import com.han.delivery.dto.FoodOptionDto;
import com.han.delivery.dto.OrderCancelDto;
import com.han.delivery.dto.OrderDetailDto;
import com.han.delivery.dto.OrderInfoDto;
import com.han.delivery.dto.OrderListDto;
import com.han.delivery.dto.StoreDetailDto;
import com.han.delivery.dto.StoreDto;
import com.han.delivery.utils.Page;
import com.han.delivery.utils.UserInfoSessionUpdate;

@Service
public class OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	AdminMapper adminMapper;
	
	@Transactional
	public long orderPriceCheck(CartListDto cartListDto) {
		 
		System.out.println("cartDetail = " + cartListDto);
 
		List<CartDto> cart = cartListDto.getCartDto();
		List<Integer> foodPriceList = orderMapper.foodPriceList(cart);
		List<Integer> optionPriceList = orderMapper.optionPriceList(cart);
		int deliveryTip = orderMapper.getDeliveryTip(cartListDto.getStoreId());
		System.out.println("foodPriceList = " + foodPriceList);
		System.out.println("optionPriceList = " + optionPriceList);
		
		int sum = 0;
		
		for (int i = 0; i < cart.size(); i++) {
			int foodPrice = foodPriceList.get(i);
			int amount = cart.get(i).getAmount();
			int optionPrice = optionPriceList.get(i);
 
			sum += (foodPrice + optionPrice) * amount;
		}
 
		return sum + deliveryTip; 
	}
	
	@Transactional
	public String order(CartListDto cartListDto, OrderInfoDto orderInfoDto, CustomUserDetails principal, HttpSession session) {
		Gson gson = new Gson();
		
		System.out.println("info = " + orderInfoDto);
		
		int total = cartListDto.getCartTotal();
		
		orderInfoDto.setStoreId(cartListDto.getStoreId());
		orderInfoDto.setTotalPrice(total);
		
		long userId = 0;
		if (principal != null) {
			userId = principal.getId();
			orderInfoDto.setUserId(userId);
		}
		
		List<CartDto> cartList = cartListDto.getCartDto();
		
		OrderDetailDto[] orderDetailDto = new OrderDetailDto[cartList.size()];
		
		
		for(int i=0;i<orderDetailDto.length;i++) {
			String cartJSON = gson.toJson(cartList.get(i));
			orderDetailDto[i] = new OrderDetailDto(orderInfoDto.getOrderNum(), cartJSON);
		}
		
		orderMapper.order(orderInfoDto);
		
		
		  Map<String, Object> orderDetailMap = new HashMap<>(); 
		  orderDetailMap.put("userId", userId);
		  orderDetailMap.put("detail", orderDetailDto); 
		  orderMapper.orderDetail(orderDetailMap);
		  
		// ?????? ????????? ??????
		  if (principal != null) {
		      String storeName = cartListDto.getStoreName();
		      int point = (int)(total * 0.01); 
		      
		      Map<String, Object> ponitUpdateMap = new HashMap<>();
		      ponitUpdateMap.put("userId", userId);
		      ponitUpdateMap.put("info", storeName);
		      ponitUpdateMap.put("point", point);
		      
		      int result = adminMapper.pointUpdate(ponitUpdateMap);
		      
		      ponitUpdateMap.put("totalPoint", principal.getPoint() + point);
		      int result2 = adminMapper.pointUpdateUser(ponitUpdateMap);
		      
		      if(result == 1 && result2 == 1) {
		          UserInfoSessionUpdate.sessionUpdate(point+"", "point", principal, session);
		      }
		  }
		  
		// ????????? ???????????? ????????? ???????????????
		  if(orderInfoDto.getUsedPoint() != 0 ) {
		      String storeName =  cartListDto.getStoreName();
		      int usedPoint =  -orderInfoDto.getUsedPoint();
		      
		      Map<String, Object> ponitUpdateMap = new HashMap<>();
		      ponitUpdateMap.put("userId", userId);
		      ponitUpdateMap.put("info", storeName);
		      ponitUpdateMap.put("point", usedPoint);
		      
		      int result = adminMapper.pointUpdate(ponitUpdateMap);
		      
		      ponitUpdateMap.put("totalPoint", principal.getPoint() + usedPoint);
		      int result2 = adminMapper.pointUpdateUser(ponitUpdateMap);
		      
		      if(result == 1 && result2 == 1) {
		          UserInfoSessionUpdate.sessionUpdate(usedPoint+"", "point", principal, session);
		      }
		  }
		 
		
		
		
		return null;
	}
	
	// ?????? ?????? ??????
	public List<OrderListDto> orderList(long userId, Page p) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
		
		System.out.println("????????? ?????? = " + p.getFirstList() + " ????????? ?????? = " + p.getLastList());
		System.out.println("????????? = " + p.getFirstPage() + " ????????? = " + p.getLastPage() );
		System.out.println("??????????????? = " + p.getPrevPage());
		System.out.println("??????????????? = " + p.getNextPage());
		return orderMapper.orderList(map);
	}
	
	// ???????????? ????????????
	public OrderListDto orderListDetail(String orderNum) {
		return orderMapper.orderListDetail(orderNum);
	}
	

	
}
