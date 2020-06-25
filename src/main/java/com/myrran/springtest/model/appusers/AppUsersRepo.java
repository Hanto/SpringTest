package com.myrran.springtest.model.appusers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppUsersRepo extends JpaRepository<AppUsers, Long>
{
    Collection<AppUsers> findByUsername(String name);
}
