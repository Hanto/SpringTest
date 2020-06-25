package com.myrran.springtest.model.demo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Data
public class UserDTO
{
    private Long id;
    private String name;
    private String email;
}
