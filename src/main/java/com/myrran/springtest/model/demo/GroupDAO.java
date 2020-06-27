package com.myrran.springtest.model.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GroupDAO extends JpaRepository<Group, Long>
{
    Collection<Group> findByName(String name);
    Collection<Group> findByNameLike(String name);
}
