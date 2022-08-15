package com.han.delivery.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderInfoDto {
	
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
	private String impUid = ""; // 아임포트 결제번호 추가
	
}