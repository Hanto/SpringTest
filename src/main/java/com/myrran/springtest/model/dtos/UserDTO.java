package com.myrran.springtest.model.dtos;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor @Data
public class UserDTO
{
    private Long id;
    private String name;
    private String email;
}
