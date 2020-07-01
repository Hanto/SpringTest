package com.myrran.springtest.model.food.dtos;

import lombok.Data;

@Data
public class NutrientDTO
{
    private long id;
    private String number;
    private String name;
    private int rank;
    private String unitName;
}
