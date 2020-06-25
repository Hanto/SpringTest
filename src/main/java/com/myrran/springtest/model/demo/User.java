package com.myrran.springtest.model.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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