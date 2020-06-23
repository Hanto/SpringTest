package com.myrran.springtest;

import com.myrran.springtest.model.Event;
import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.repo.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@Component
class Startup implements CommandLineRunner
{
    private final GroupRepository repository;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Startup(GroupRepository repository)
    {   this.repository = repository;}

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public void run(String... strings)
    {
        log.info("Startup");
    }
}