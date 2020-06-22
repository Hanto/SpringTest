package com.myrran.springtest.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor @AllArgsConstructor @Data
@Entity
public class User
{
    @Id
    private String id;
    private String name;
    private String email;
}