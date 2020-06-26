package com.myrran.springtest.model.food;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity @Table (name = "food_portions")
public class FoodPortions
{
    @Id
    private long id;
    private int gramWeight;
    private String portionDescription;
}
