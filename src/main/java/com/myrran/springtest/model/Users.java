package com.myrran.springtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//circular dependencies should be excluded from ToString & HashCodes
@Data @EqualsAndHashCode(exclude = "roles") @ToString(exclude = "roles")
@Entity
public class Users
{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private boolean enabled;
    // if joining table not specified, it automatically generates it
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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
