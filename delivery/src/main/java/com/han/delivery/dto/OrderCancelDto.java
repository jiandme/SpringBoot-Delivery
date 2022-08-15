package com.han.delivery.dto;

import java.util.Arrays;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderCancelDto {
 
	private String OrderNum;
	private long userId;
	private int totaloPrice;
	private int usedPoint;
	private int deleveryTip;
	
	private String impUid; // 아임포트 결제번호
}
