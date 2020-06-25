package com.myrran.springtest.model.appusers;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity @Table (name = "appusers")
public class AppUsers
{
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private boolean enabled;
    @JoinTable
    (
        name = "appusers_to_approles",
        joinColumns = { @JoinColumn(name = "appuserid") },
        inverseJoinColumns = { @JoinColumn(name = "approlid") }
    )
    @ManyToMany(fetch = FetchType.EAGER)//(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<AppRoles> roles = new HashSet<>();

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public void addRol(AppRoles rol)
    {   roles.add(rol); }

    public void removeRol(AppRoles rol)
    {   roles.remove(rol); }
}
