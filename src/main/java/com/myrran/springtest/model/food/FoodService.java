package com.myrran.springtest.model.food;

import com.myrran.springtest.model.food.dtos.FoodDTO;
import com.myrran.springtest.model.food.entities.Food;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class FoodService
{
    private final FoodDAO foodDAO;
    private final ModelMapper modelMapper;

    public FoodService(FoodDAO foodDAO, ModelMapper modelMapper)
    {
        this.foodDAO = foodDAO;
        this.modelMapper = modelMapper;
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public FoodDTO getFoodDTOByID(Long id)
    {
        Food food = foodDAO.findById(id).orElse(null);
        return food == null ? null : modelMapper.map(food, FoodDTO.class);
    }

    public Collection<FoodDTO>getFoodDTOByNutrient(String nutrient, int page, int pageSize)
    {
        Collection<Food> foods = foodDAO.findByNutrientName(nutrient, page, pageSize);
        return foods.stream().map(food -> modelMapper.map(food, FoodDTO.class)).collect(Collectors.toSet());
    }
}
