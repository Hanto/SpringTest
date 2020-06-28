package com.myrran.springtest.web;

import com.myrran.springtest.model.food.FoodService;
import com.myrran.springtest.model.food.dtos.FoodDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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

    @GetMapping("/food/search")
    public Collection<FoodDTO>getFood(@RequestParam Map<String,String> allParams)
    {
        String nutrient = allParams.get("nutrient");
        String page = allParams.get("page");
        String pageSize = allParams.get("pagesize");
        log.info("param nutrient: {} {} {}", nutrient, page, pageSize);

        Integer pageI = Integer.parseInt(page);
        Integer pageSizeI = Integer.parseInt(pageSize);
        return foodService.getFoodDTOByNutrient(nutrient, pageI, pageSizeI);
    }
}
