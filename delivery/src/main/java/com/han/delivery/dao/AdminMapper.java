package com.han.delivery.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.han.delivery.dto.FoodDto;
import com.han.delivery.dto.OrderCancelDto;
import com.han.delivery.dto.OrderListDto;
import com.han.delivery.dto.SalesDetailDto;
import com.han.delivery.dto.SalesDto;
import com.han.delivery.dto.StoreDto;

@Mapper
public interface AdminMapper {
	
	//포인트 테이블 내역 업데이트
	public int pointUpdate(Map<String, Object> ponitUpdateMap);
	
	//유저 테이블 포인트 업데이트
	public int pointUpdateUser(Map<String, Object> ponitUpdateMap);
	
	//운영중인 매장 목록
	public List<StoreDto> myStore(long userId);
	
	//매장 정보 수정
	public void storeInfoUpdate(StoreDto storeDto);
	
	//메뉴 추가
	public long addMenu(FoodDto foodDto);

	//메뉴 옵션 추가
	public void addMenuOption(List<Map<String, Object>> optionList);
	
	//메뉴 수정
	public void updateMenu(Map<String, Object> map);
	
	//메뉴 옵션 삭제
	public void deleteMenuOption(long foodId);
	
	//메뉴 삭제
	public void deleteMenu(long storeId, long[] deleteNumber);
	
	//사장님 답글
	public void bossComment(Map<String, Object> map);
	
	//주문목록
	public List<OrderListDto> orderList(Map<String, Object> map);
	
	//주문수락
	public void orderAccept(Map<String, Object> map);
	
	//주문취소
	public void orderCancle(OrderCancelDto orderCancelDto);
	
	//주문완료
	public void orderComplete(Map<String, Object> map);

	//특정일 판매 데이터
	public List<SalesDetailDto> salesDetail(Map<String, Object> map);
	
	//특정기간 매출 데이터
	public List<SalesDto> sales(Map<String, Object> map);
	
	//관리중인 매장 리스트
	public List<Long> getMyStoreId(long userId);

	
}