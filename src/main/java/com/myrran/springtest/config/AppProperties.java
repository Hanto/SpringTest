package com.myrran.springtest.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = {"classpath:${properties}"})
public class AppProperties
{
    @Value("${environment}")
    private String environment;
}
