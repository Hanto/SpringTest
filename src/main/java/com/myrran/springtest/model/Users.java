package com.myrran.springtest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity @Table (name = "Users")
public class Users
{
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private boolean enabled;
    // if joining table not specified, it automatically generates it
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable
    (
        name = "user_roles",
        joinColumns = { @JoinColumn(name = "fk_user") },
        inverseJoinColumns = { @JoinColumn(name = "fk_rol") }
    )
    private final Set<Roles> roles = new HashSet<>();

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public void addRol(Roles rol)
    {
        roles.add(rol);
        rol.getUsers().add(this);
    }

    public void removeRol(Roles rol)
    {
        roles.remove(rol);
        rol.getUsers().remove(this);
    }
}
