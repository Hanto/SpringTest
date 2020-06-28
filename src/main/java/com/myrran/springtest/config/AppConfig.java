package com.myrran.springtest.config;

import com.myrran.springtest.model.food.dtos.FoodDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig
{
    @Bean @Scope("singleton")
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        FoodDTO.getMappings().forEach(modelMapper::addMappings);
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {   return builder.build(); }
}
