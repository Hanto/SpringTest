package com.myrran.springtest.model.repo;

import com.myrran.springtest.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UsersRepository extends JpaRepository<Users, Long>
{
    Collection<Users> findByName(String name);
}
