package com.myrran.springtest.model.appusers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//circular dependencies should be excluded from ToString & HashCodes
@Data @EqualsAndHashCode(exclude = "users") @ToString(exclude = "users")
@Entity @Table (name = "authorities")
public class AppRoles
{
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String authority;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER) //, cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<AppUsers> users = new HashSet<>();

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    public void addUser(AppUsers user)
    {
        users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(AppUsers user)
    {
        users.remove(user);
        user.getRoles().remove(this);
    }
}
