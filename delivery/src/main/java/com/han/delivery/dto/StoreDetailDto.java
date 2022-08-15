package com.han.delivery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailDto {
	
	private StoreDto storeInfo;
	private List<FoodDto> foodList;
	private List<ReviewDto> reviewList;
	
}