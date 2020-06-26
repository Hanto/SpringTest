package com.myrran.springtest.model.food;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity @Table(name = "nutrients")
public class Nutrient
{
    @Id
    private long id;
    private String number;
    private String name;
    private int rank;
    private String unitName;
}
