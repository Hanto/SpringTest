package com.myrran.springtest.model.appusers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppRolesRepo extends JpaRepository<AppRoles, Long>
{
    Collection<AppRoles> findByAuthority(String rolName);
}
