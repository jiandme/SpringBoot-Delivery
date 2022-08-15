package com.han.delivery.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderListDto {
	
	private String orderNum;
	private long storeId;
	private long userId;
	private Date orderDate;
	private String DeliveryStatus;
	private int DeliveryAddress1;
	private String DeliveryAddress2;
	private String DeliveryAddress3;
	private String payMethod;
	private int totalPrice;
	private int usedPoint;
	private String phone;
	private String request;
	private String foodInfo;
	private String storeName;
	private String storeImg;
	private String storeThumb;
	private String deliveryTip;
	private String reviewContent;
	private float score;
	private String reviewImg;
	
	private int listCount; // 목록 총 갯수
	
	private int count1; // 주문대기중 갯수
	private int count2; // 주문처리중 갯수
	private int count3; // 주문취소 갯수
	private int count4; // 주문완료 갯수	
	
	private String impUid; // 아임포트 결제번호 추가
	
	
}