package com.myrran.springtest.model.food.dtos;

import com.myrran.springtest.model.food.entities.FoodNutrient;
import com.myrran.springtest.model.food.entities.FoodPortions;
import lombok.Data;
import org.modelmapper.PropertyMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class FoodDTO
{
    private long fdcId;
    private String foodClass;
    private String description;
    private String amounts;
    private Set<FoodNutrientDTO> foodNutrients;
    private Set<FoodPortions> foodPortions;

    // MAPPINGS:
    //--------------------------------------------------------------------------------------------------------

    public static Collection<PropertyMap<?,?>> getMappings()
    {
        Set<PropertyMap<?,?>>mappings = new HashSet<>();
        mappings.add(mapFoodNutrientToDTO());
        mappings.add(mapFoodNutrientToEntity());
        return mappings;
    }

    private static PropertyMap<FoodNutrient, FoodNutrientDTO> mapFoodNutrientToDTO()
    {
        return new PropertyMap <FoodNutrient, FoodNutrientDTO>()
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
    }

    private static PropertyMap<FoodNutrientDTO, FoodNutrient> mapFoodNutrientToEntity()
    {
        return new PropertyMap <FoodNutrientDTO, FoodNutrient>()
        {
            protected void configure()
            {
                map().setId(source.getId());
                map().setAmount(source.getAmount());
                map().getNutrient().setId(source.getId());
                map().getNutrient().setNumber(source.getNumber());
                map().getNutrient().setName(source.getName());
                map().getNutrient().setRank(source.getRank());
                map().getNutrient().setUnitName(source.getUnitName());
            }
        };
    }
}