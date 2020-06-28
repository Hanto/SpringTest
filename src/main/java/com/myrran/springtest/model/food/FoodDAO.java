package com.myrran.springtest.model.food;

import com.myrran.springtest.model.food.entities.Food;
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
        "select * from NUTRIENTS as n, FOOD_NUTRIENTS as fn, \n" +
        "    FOOD_TO_FOOD_NUTRIENT as ffn, FOODS as f \n" +
        "where \n" +
        "    n.ID = fn.NUTRIENT_ID and \n" +
        "    lower(n.NAME) like lower(?1) and \n" +
        "    fn.AMOUNT > 0 and \n" +
        "    fn.ID = ffn.FOOD_NUTRIENTS_ID and \n" +
        "    ffn.FOOD_FDC_ID = f.FDC_ID \n" +
        "order by fn.AMOUNT desc;"
    )
    Collection<Food>findByNutrientName(String nutrientName);

    @Query
    (
        nativeQuery = true,
        value =
        "select * from NUTRIENTS as n, FOOD_NUTRIENTS as fn, \n" +
        "    FOOD_TO_FOOD_NUTRIENT as ffn, FOODS as f \n" +
        "where \n" +
        "    n.ID = fn.NUTRIENT_ID and \n" +
        "    n.ID = ?1 and \n" +
        "    fn.AMOUNT > 0 and \n" +
        "    fn.ID = ffn.FOOD_NUTRIENTS_ID and \n" +
        "    ffn.FOOD_FDC_ID = f.FDC_ID \n" +
        "order by fn.AMOUNT desc;"
    )
    Collection<Food>findByNutrientID(Long nutriendID);
}
