package com.myrran.springtest.model.food;

import com.myrran.springtest.model.food.dtos.FoodDTO;
import com.myrran.springtest.model.food.entities.Food;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService
{
    private @Autowired FoodDAO foodDAO;
    private @Autowired ModelMapper modelMapper;

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public FoodDTO getFoodDTOByID(Long id)
    {
        Food food = foodDAO.findById(id).orElse(null);
        return food == null ? null : modelMapper.map(food, FoodDTO.class);
    }
}
