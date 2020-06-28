package com.myrran.springtest.model.food.dtos;

import lombok.Data;

@Data
public class FoodNutrientDTO
{
    private long id;
    private int amount;
    private Long nutriendID;
    private String number;
    private String name;
    private int rank;
    private String unitName;
}
