package com.myrran.springtest.model;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor @AllArgsConstructor @Builder @Data
@Entity
public class User
{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
}