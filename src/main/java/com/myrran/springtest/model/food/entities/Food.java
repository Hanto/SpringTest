package com.myrran.springtest.model.food.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity @Table (name = "foods")
public class Food
{
    @Id
    private long fdcId;
    private String foodClass;
    private String description;
    @JoinTable( name = "food_to_food_nutrient")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<FoodNutrient> foodNutrients;
    @JoinTable( name = "food_to_food_portion")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<FoodPortions>foodPortions;
}
