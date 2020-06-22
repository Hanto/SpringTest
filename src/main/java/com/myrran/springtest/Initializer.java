package com.myrran.springtest;

import com.myrran.springtest.model.Event;
import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.repo.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@Component
class Initializer implements CommandLineRunner
{
    private final GroupRepository repository;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Initializer(GroupRepository repository)
    {   this.repository = repository;}

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public void run(String... strings)
    {
        Stream.of(
            "Johana",
            "Pingüa",
            "PajaringUa",
            "Hulka")
            .forEach(name -> repository.save(
                Group.builder()
                .name(name)
                .build()
            ));

        log.info("Ivan");

        Group djug = repository.findByName("Johana");

        Event e = Event.builder().
            title("Full Stack Reactive")
            .description("Reactive with Spring Boot + React")
            .date(Instant.parse("2018-12-12T18:00:00.000Z"))
            .build();

        djug.setEvents(Collections.singleton(e));
        repository.save(djug);
        repository.findAll().forEach(System.out::println);
    }
}