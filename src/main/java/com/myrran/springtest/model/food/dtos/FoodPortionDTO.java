package com.myrran.springtest.model.food.dtos;

import lombok.Data;

@Data
public class FoodPortionDTO
{
    private long id;
    private int gramWeight;
    private String portionDescription;
}
