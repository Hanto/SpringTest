package com.myrran.springtest.model.food.services;

import com.myrran.springtest.model.food.dao.NutrientDAO;
import com.myrran.springtest.model.food.dtos.NutrientDTO;
import com.myrran.springtest.model.food.entities.Nutrient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class NutrientService
{
    private final NutrientDAO nutrientDAO;
    private final ModelMapper modelMapper;

    public NutrientService(NutrientDAO nutrientDAO, ModelMapper modelMapper)
    {
        this.nutrientDAO = nutrientDAO;
        this.modelMapper = modelMapper;
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public Collection<NutrientDTO>getFoodDTOByNutrient(String nutrientName)
    {
        Collection<Nutrient>nutrients = nutrientDAO.findByNameLikeIgnoreCase(nutrientName);
        return nutrients.stream().map(nutrient -> modelMapper.map(nutrient, NutrientDTO.class)).collect(Collectors.toSet());
    }
}