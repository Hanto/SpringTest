package com.myrran.springtest.web;

import com.myrran.springtest.model.food.services.FoodService;
import com.myrran.springtest.model.food.services.NutrientService;
import com.myrran.springtest.model.food.dtos.FoodDTO;
import com.myrran.springtest.model.food.dtos.NutrientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController @RequestMapping("/api")
public class FoodController
{
    private final FoodService foodService;
    private final NutrientService nutrientService;

    public FoodController(FoodService foodService, NutrientService nutrientService)
    {
        this.foodService = foodService;
        this.nutrientService = nutrientService;
    }


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

        int pageI = Integer.parseInt(page);
        int pageSizeI = Integer.parseInt(pageSize);
        return foodService.getFoodDTOByNutrient(nutrient, pageI, pageSizeI);
    }

    @GetMapping("/nutrient/search")
    public Collection<NutrientDTO>getNutrient(@RequestParam Map<String,String> allParams)
    {
        String nutrientName = allParams.get("nutrient");
        log.info("param nutrient: {}", nutrientName);

        return nutrientService.getFoodDTOByNutrient(nutrientName);
    }
}
