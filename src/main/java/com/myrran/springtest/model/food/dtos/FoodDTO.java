package com.myrran.springtest.model.food.dtos;

import com.myrran.springtest.model.food.entities.FoodNutrient;
import com.myrran.springtest.model.food.entities.FoodPortions;
import lombok.Data;
import org.modelmapper.PropertyMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
public class FoodDTO
{
    private long fdcId;
    private String foodClass;
    private String description;
    private Set<FoodNutrientDTO> foodNutrients;
    private Set<FoodPortions> foodPortions;

    // MAPPINGS:
    //--------------------------------------------------------------------------------------------------------

    public static Collection<PropertyMap<?, ?>> getMappings()
    {
        PropertyMap<FoodNutrient, FoodNutrientDTO> foodNutrientMappings = new PropertyMap <FoodNutrient, FoodNutrientDTO>()
        {
            protected void configure()
            {
                map().setId(source.getId());
                map().setAmount(source.getAmount());
                map().setNutriendID(source.getNutrient().getId());
                map().setNumber(source.getNutrient().getNumber());
                map().setName(source.getNutrient().getName());
                map().setRank(source.getNutrient().getRank());
                map().setUnitName(source.getNutrient().getUnitName());
            }
        };

        return Collections.singleton(foodNutrientMappings);
    }
}