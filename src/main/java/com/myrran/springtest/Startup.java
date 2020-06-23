package com.myrran.springtest;

import com.myrran.springtest.model.repo.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
class Startup implements CommandLineRunner
{
    private final GroupRepository repository;
    private @Autowired RestTemplate restTemplate;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Startup(GroupRepository repository)
    {   this.repository = repository;}

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public void run(String... strings)
    {
        log.info("Startup");

        String originLan = "en";
        String destinyLan= "es";
        String text = "Hello Friend, how are you?";

        String translate = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + originLan +
            "&tl=" + destinyLan +"&dt=t&q=" + text;

        String food = "https://api.nal.usda.gov/ndb/reports/?ndbno=01120&type=b&format=xml&api_key=DEMO_KEY";

        Object object = restTemplate.getForObject(translate, String.class);

        log.info("OBJECT: {}", object);
    }
}