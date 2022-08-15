package com.han.delivery.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SalesDto {
	
	private Date orderDate;
	private int total;
	
}