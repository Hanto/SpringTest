package com.myrran.springtest;

import com.myrran.springtest.model.Event;
import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.GroupRepository;
import lombok.extern.slf4j.Slf4j;
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

    public Initializer(GroupRepository repository)
    {   this.repository = repository; }

    @Override public void run(String... strings)
    {
        Stream.of(
            "Denver JUG",
            "Utah JUG",
            "Seattle JUG",
            "Richmond JUG")
            .forEach(name -> repository.save(new Group(name)));

        log.info("Ivan");

        Group djug = repository.findByName("Denver JUG");

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
