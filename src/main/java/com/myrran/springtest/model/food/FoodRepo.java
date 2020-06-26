package com.myrran.springtest.model.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FoodRepo extends JpaRepository<Food, Long>
{
    Collection<Food>findByDescription(String description);
    Collection<Food>findByDescriptionIgnoreCaseLike(String description);
}
