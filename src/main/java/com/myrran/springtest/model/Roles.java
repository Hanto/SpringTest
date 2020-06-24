package com.myrran.springtest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//circular dependencies should be excluded from ToString & HashCodes
@Data @EqualsAndHashCode(exclude = "users") @ToString(exclude = "users")
@Entity @Table (name = "Authorities")
public class Roles
{
    @Id @GeneratedValue
    private Long id;
    private String authority;
    //mapped by specifies the joining table
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER) //, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<Users> users = new HashSet<>();

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public void addUser(Users user)
    {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(Users user)
    {
        users.remove(user);
        user.getRoles().remove(this);
    }
}
