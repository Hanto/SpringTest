package com.myrran.springtest.model.food.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity @Table (name = "food_nutrients")
public class FoodNutrient
{
    @Id
    private long id;
    private int amount;
    @JoinColumn( name = "nutrient_id")
    @ManyToOne(fetch =  FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Nutrient nutrient;
}