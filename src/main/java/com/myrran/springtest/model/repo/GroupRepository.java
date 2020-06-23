package com.myrran.springtest.model.repo;

import com.myrran.springtest.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GroupRepository extends JpaRepository<Group, Long>
{
    Collection<Group> findByName(String name);
}
