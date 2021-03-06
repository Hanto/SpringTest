package com.myrran.springtest.model.demo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor @Data
public class GroupDTO
{
    private Long id;
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private UserDTO user;
    private Set<EventDTO> events;
}