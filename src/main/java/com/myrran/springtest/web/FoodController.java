package com.myrran.springtest.web;

import com.myrran.springtest.model.food.FoodService;
import com.myrran.springtest.model.food.dtos.FoodDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController @RequestMapping("/api")
public class FoodController
{
    private final FoodService foodService;

    public FoodController(FoodService foodService)
    {   this.foodService = foodService;}

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @GetMapping("/food/{id}")
    public ResponseEntity<?> getFood(@PathVariable Long id)
    {
        log.info("Request to submit food with the id: {}", id);
        FoodDTO dto = foodService.getFoodDTOByID(id);

        return dto != null ?
            ResponseEntity.ok().body(dto) :
            ResponseEntity.notFound().build();
    }
}
