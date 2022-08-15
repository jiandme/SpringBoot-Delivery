package com.han.delivery.api;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.han.delivery.config.auth.CustomUserDetails;
import com.han.delivery.dao.AuthMapper;
import com.han.delivery.dto.CartDto;
import com.han.delivery.dto.FoodDto;
import com.han.delivery.dto.FoodOptionDto;
import com.han.delivery.dto.OrderCancelDto;
import com.han.delivery.dto.OrderListDto;
import com.han.delivery.dto.ReviewDto;
import com.han.delivery.dto.SalesDto;
import com.han.delivery.dto.StoreDto;
import com.han.delivery.service.AdminService;
import com.han.delivery.service.AuthService;
import com.han.delivery.service.FindService;
import com.han.delivery.service.PaymentService;
import com.han.delivery.service.StoreService;
import com.han.delivery.service.UserService;
import com.han.delivery.utils.FileUpload;
import com.han.delivery.utils.FoodInfoFromJson;
import com.han.delivery.utils.UserInfoSessionUpdate;



@RestController
public class AdminApiController {
	
	@Autowired
	AdminService adminService;
	@Autowired
	FileUpload fileUpload;
	@Autowired
	private PaymentService paymentService;


	
	@PatchMapping("/api/admin/management/storeInfo")
	public ResponseEntity<StoreDto> storeInfoUpdate(StoreDto storeDto, MultipartFile file) throws IOException {
		if(!file.isEmpty()){
			String img = fileUpload.uploadImg(file, "storeImg");
			storeDto.setStoreImg(img);
			storeDto.setStoreThumb(img);
		}
		System.out.println(storeDto.toString());
		adminService.storeInfoUpdate(storeDto);
		return ResponseEntity.ok().body(storeDto);
	}

	
	@PostMapping("/api/admin/management/menu")
	public ResponseEntity<FoodDto> addMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, MultipartFile file) throws IOException {
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		} else {
			String img = fileUpload.uploadImg(file, "foodImg");
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		}
		
		adminService.addMenu(foodDto, foodOption, foodOptionPrice);
		return ResponseEntity.ok().body(foodDto);
	}
	
	@PatchMapping("/api/admin/management/menu")
	public ResponseEntity<FoodDto> updateMenu(FoodDto foodDto, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId, MultipartFile file) throws IOException {
		
		System.out.println(foodDto);
		if(!file.isEmpty()){
			String img = fileUpload.uploadImg(file, "foodImg");
			foodDto.setFoodImg(img);
			foodDto.setFoodThumb(img);
		}

		adminService.updateMenu(foodDto, foodOption, foodOptionPrice, optionId);
		return ResponseEntity.ok().body(foodDto);
	}
	
	@DeleteMapping("/api/admin/management/menu")
	public ResponseEntity<String> deleteMenu(long storeId, long[] deleteNumber) {
		adminService.deleteMenu(storeId, deleteNumber);
		return ResponseEntity.ok().body("삭제 완료");
	}
	
	@PatchMapping("/api/admin/management/bossComment")
	public ResponseEntity<String> bossComment(long storeId, String orderNum, String bossComment) throws IOException {
		String reviewContent = adminService.bossComment(storeId, orderNum, bossComment);
		return ResponseEntity.ok().body(reviewContent);
	}

	
	//주문접수목록
	@GetMapping("/api/admin/management/orderList")
	public ResponseEntity<Map<String, Object>> orderList(long storeId, String list, int page) {
	 
		System.out.println(storeId);
		System.out.println(list);
		System.out.println("page = " + page);
		List<OrderListDto> orderListDto = adminService.orderList(storeId, list, page);
		
		Map<String, Object> map = new HashMap<>();
		List<List<CartDto>> menuList = new ArrayList<>();
		System.out.println(orderListDto);
		if(orderListDto.size() != 0 && orderListDto.get(0).getFoodInfo() != null) {
			for (int i=0;i<orderListDto.size();i++) {
				menuList.add(FoodInfoFromJson.foodInfoFromJson(orderListDto.get(i).getFoodInfo()));
			}
		}
		
		System.out.println(menuList);
		map.put("orderList", orderListDto);
		map.put("cartList", menuList);
		return ResponseEntity.ok().body(map);
	}
	
	
	//주문수락 
	@PatchMapping("/api/admin/management/orderAccept")
	public ResponseEntity<String> orderAccept(String orderNum, long userId) {
		//			userId == 0 비회원
		adminService.orderAccept(orderNum, userId);
		
		return ResponseEntity.ok().body("주문접수완료");
	}
	
	//주문취소
	@PatchMapping("/api/admin/management/orderCancle")
	public ResponseEntity<String> orderCancle(OrderCancelDto orderCancelDto) throws IOException {
		System.out.println(orderCancelDto.toString());
	    if(!"".equals(orderCancelDto.getImpUid())) {
	        String token = paymentService.getToken();
	        int amount = paymentService.paymentInfo(orderCancelDto.getImpUid(), token);
	        paymentService.payMentCancle(token, orderCancelDto.getImpUid(), amount, "관리자 취소");
	    }
		
		adminService.orderCancel(orderCancelDto);

		return ResponseEntity.ok().body("주문취소완료");
	}
	
	//주문 완료
	@PatchMapping("/api/admin/management/orderComplete")
	public ResponseEntity<String> orderComplete(String orderNum, long userId) {
		//			userId == 0 비회원
		adminService.orderComplete(orderNum, userId);
		return ResponseEntity.ok().body("주문완료");
	}
	
	// 특정일 판매 데이터
	@GetMapping("/api/admin/management/salesDetail")
	public ResponseEntity<Map<String, Object>> salesDetail(long storeId, String date, String sort){
		
		System.out.printf("가게 번호 : %d, 날짜 : %s ", storeId, date);
		Map<String, Object> salseToday = adminService.salesDetail(storeId, date, sort);
		
		return ResponseEntity.ok().body(salseToday);
	}
	
	//특정 기간 매출 데이터
	@GetMapping("/api/admin/management/sales")
	public ResponseEntity<List<SalesDto>> sales(long storeId, String date, String term) {

		
		System.out.printf("가게 번호 : %d, 날짜 : %s ", storeId, date);
		List<SalesDto> sales = adminService.sales(storeId,date, term);
		return ResponseEntity.ok().body(sales);
	}
	


}
