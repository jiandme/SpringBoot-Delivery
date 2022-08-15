package com.han.delivery.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.han.delivery.dto.CartDto;
import com.han.delivery.dto.CartListDto;
import com.han.delivery.dto.FoodOptionDto;
import com.han.delivery.dto.StoreDto;
import com.han.delivery.service.StoreService;



@RestController
public class CartApiController {
	
	
	// 장바구니 추가
	@PostMapping("/api/cart")
	public CartListDto addCart(CartDto cartDto, long storeId, String storeName, int deliveryTip, HttpSession session) {
		
		//기존 Session에서 저장된 장바구니 목록을 가져옴
		CartListDto cartListDto = (CartListDto) session.getAttribute("cartList");
		
		cartDto.totalPriceCalc();
		//Session에 저장된 장바구니 목록이 없을시
		if(cartListDto == null) {
			List<CartDto> newCart = new ArrayList<>();
			newCart.add(cartDto);
			cartListDto = new CartListDto(storeId, storeName, cartDto.getTotalPrice(), deliveryTip, newCart);
			
		} else { //저장된 장바구니 목록이 있을시
			List<CartDto> prevCart = cartListDto.getCartDto();
			int prevCartTotal = cartListDto.getCartTotal();
			cartListDto.setCartTotal(prevCartTotal + cartDto.getTotalPrice());
			
			// 이미 장바구니에 추가된 메뉴일때 
			if(prevCart.contains(cartDto)) {
				int cartIndex = prevCart.indexOf(cartDto);
				int amount = cartDto.getAmount();
				
				CartDto newCart = prevCart.get(cartIndex);
				int newAmount = newCart.getAmount() + amount;
				
				newCart.setAmount(newAmount);
				newCart.totalPriceCalc();
				prevCart.set(cartIndex, newCart);
				
			} else { // 장바구니에 추가되어 있지 않은 메뉴일때
				prevCart.add(cartDto);
			}
		}
		
		session.setAttribute("cartList", cartListDto);
        
        System.out.println("cartList = " + cartListDto);
 
		return cartListDto;
		
	}
	
	@GetMapping("/api/cart")
	public CartListDto cartList(HttpSession session) {
		CartListDto cartList = (CartListDto) session.getAttribute("cartList");
		if (cartList != null) {
			return cartList;
		}
		return null;
	}
	
	
	@DeleteMapping("/api/cart")
	public void deleteAllCart(HttpSession session) {
		session.removeAttribute("cartList");
	}
 
	@DeleteMapping("/api/cart/{index}")
	public CartListDto deleteOneCart(@PathVariable int index, HttpSession session) {
		CartListDto cartList = (CartListDto) session.getAttribute("cartList");
		if (cartList == null) {
			return null;
		}
		int cartTotal = cartList.getCartTotal();
		List<CartDto> cart = cartList.getCartDto();
		int removeCartPrice = cart.get(index).getTotalPrice();
		
		cart.remove(index);
		
		if(cart.size() == 0) {
			session.removeAttribute("cartList");
			return null;
		}
		
		cartTotal -=  removeCartPrice;
		cartList.setCartTotal(cartTotal);
		return cartList;
	}
	
}
