package com.myrran.springtest;

import com.myrran.springtest.config.AppProperties;
import com.myrran.springtest.model.*;
import com.myrran.springtest.model.repo.GroupRepository;
import com.myrran.springtest.model.repo.RolesRepository;
import com.myrran.springtest.model.repo.UsersRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

//@Transactional
@SpringBootTest
class SpringtestApplicationTests
{
    private @Autowired RolesRepository rolesRepo;
    private @Autowired UsersRepository usersRepo;
    private @Autowired GroupRepository groupRepository;
    private @Autowired ModelMapper modelMapper;
    private @Autowired AppProperties properties;
    private @Autowired PasswordEncoder encoder;

    @Test void contextLoads()
    {
        Users user = new Users();
        user.setUsername("ivan");
        user.setPassword("1131");

        Roles rol = new Roles();
        rol.setAuthority("Admin");

        user.addRol(rol);

        usersRepo.save(user);
        rolesRepo.save(rol); // not necessary with cascade persist/merge

        Collection<Users> userResult = usersRepo.findByUsername("ivan");
        Collection<Roles> rolResult = rolesRepo.findByAuthority("Admin");

        System.out.println("done");

        Users deletedUser = userResult.stream().findFirst().orElse(null);

        deletedUser.removeRol(rol);
        deletedUser = usersRepo.save(deletedUser);

        usersRepo.delete(deletedUser);

        userResult = usersRepo.findByUsername("ivan");

        System.out.println("done");
    }

    @Test public void pum()
    {
        System.out.println("ENV: "+ properties.getEnvironment());

        User user = new User();
        user.setName("Johana");

        Event event = new Event();
        event.setAttendees(Collections.singleton(user));
        event.setDescription("EVento");

        Group group = new Group();
        group.setAddress("Obispo");
        group.setCity("Valencia");
        group.setCountry("Spain");
        group.setName("Nutri Group");
        group.setUser(user);
        group.setEvents(Collections.singleton(event));

        Group result1 = groupRepository.save(group);

        System.out.println(result1.getName());

        Collection<Group> result2 = groupRepository.findByName("Nutri Group");

        System.out.println("done");
    }

    @Test public void pim()
    {
        Collection<Users> user = usersRepo.findByUsername("Admin");
        Collection<Roles> role = rolesRepo.findByAuthority("ADMIN");

        if (role.isEmpty() && user.isEmpty())
        {
            Roles adminRol = new Roles();
            adminRol.setAuthority("ADMIN");
            adminRol = rolesRepo.save(adminRol);

            Users adminUser = new Users();
            adminUser.setEnabled(true);
            adminUser.setUsername("admin");
            adminUser.setEnabled(true);
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.addRol(adminRol);
            usersRepo.save(adminUser);
        }
    }
}
