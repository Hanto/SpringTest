package com.myrran.springtest.model.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface FoodDAO extends JpaRepository<Food, Long>
{
    Collection<Food>findByDescription(String description);
    Collection<Food>findByDescriptionIgnoreCaseLike(String description);

    @Query
    (
        nativeQuery = true,
        value =
        "select * from NUTRIENTS as n, FOOD_NUTRIENTS as fn, " +
        "    FOOD_TO_FOOD_NUTRIENT as ffn, FOODS as f \n" +
        "where\n" +
        "    n.ID = fn.NUTRIENT_ID and\n" +
        "    n.NAME = ?1 and\n" +
        "    fn.ID = ffn.FOOD_NUTRIENTS_ID and\n" +
        "    ffn.FOOD_FDC_ID = f.FDC_ID;"
    )
    Collection<Food>findByNutrient(String nutrientName);
}
