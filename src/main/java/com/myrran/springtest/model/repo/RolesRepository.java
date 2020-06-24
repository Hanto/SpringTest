package com.myrran.springtest.model.repo;

import com.myrran.springtest.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RolesRepository extends JpaRepository<Roles, Long>
{
    Collection<Roles> findByAuthority(String rolName);
}
