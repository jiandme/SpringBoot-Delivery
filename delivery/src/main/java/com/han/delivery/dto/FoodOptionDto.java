package com.han.delivery.dto;

import lombok.Data;

@Data
public class FoodOptionDto {
    private long id;
    private long foodId;
    private String optionName;
    private long optionPrice;
}