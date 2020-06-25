package com.myrran.springtest.model.demo.dtos;

import com.myrran.springtest.model.demo.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor @Data
public class EventDTO
{
    private Long id;
    private Instant date;
    private String title;
    private String description;
    private Set<User> attendees;
}
