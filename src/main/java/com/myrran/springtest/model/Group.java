package com.myrran.springtest.model;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Builder @Data
@Entity @Table(name = "user_group")
public class Group
{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Event> events;
}