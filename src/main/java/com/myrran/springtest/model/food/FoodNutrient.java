package com.myrran.springtest.model.food;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity @Table (name = "food_nutrients")
public class FoodNutrient
{
    @Id
    private long id;
    private int amount;
    @JoinColumn( name = "food_nutrient_to_nutrient")
    @ManyToOne(fetch =  FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Nutrient nutrient;
}
