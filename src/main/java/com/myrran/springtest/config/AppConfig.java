package com.myrran.springtest.config;

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
    {   return new ModelMapper(); }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {   return builder.build(); }

    /*@Bean
    public DataSource dataSource() {

        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
            .setType(EmbeddedDatabaseType.DERBY) //.H2 or .DERBY
            //.addScript("db/sql/create-db.sql")
            //.addScript("db/sql/insert-data.sql")
            .build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }*/
}
