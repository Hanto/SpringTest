package com.myrran.springtest.model.food.dao;

import com.myrran.springtest.model.food.entities.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface NutrientDAO extends JpaRepository<Nutrient, Long>
{
    Collection<Nutrient> findByNameLikeIgnoreCase(String nutrientName);
}
