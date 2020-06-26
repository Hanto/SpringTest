package com.myrran.springtest.test;

import com.myrran.springtest.config.AppProperties;
import com.myrran.springtest.model.appusers.AppRoles;
import com.myrran.springtest.model.appusers.AppRolesRepo;
import com.myrran.springtest.model.appusers.AppUsers;
import com.myrran.springtest.model.appusers.AppUsersRepo;
import com.myrran.springtest.model.demo.Event;
import com.myrran.springtest.model.demo.Group;
import com.myrran.springtest.model.demo.GroupRepo;
import com.myrran.springtest.model.demo.User;
import com.myrran.springtest.model.food.Food;
import com.myrran.springtest.model.food.FoodRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@SpringBootTest
class GlobalTests
{
    private @Autowired
    AppRolesRepo appRolesRepo;
    private @Autowired
    AppUsersRepo appUsersRepo;
    private @Autowired
    GroupRepo groupRepo;
    private @Autowired ModelMapper modelMapper;
    private @Autowired AppProperties properties;
    private @Autowired PasswordEncoder encoder;
    private @Autowired RestTemplate restTemplate;
    private @Autowired FoodRepo foodRepo;

    // BEFORE:
    //--------------------------------------------------------------------------------------------------------

    @BeforeEach
    public void before()
    {
        Collection<AppUsers> users = appUsersRepo.findByUsername("admin");
        Collection<AppRoles> roles = appRolesRepo.findByAuthority("ADMIN");

        if (!users.isEmpty())
            users.forEach(user -> appUsersRepo.deleteById(user.getId()));

        if (!roles.isEmpty())
            roles.forEach(rol -> appRolesRepo.deleteById(rol.getId()));

        users = appUsersRepo.findByUsername("admin");
        roles = appRolesRepo.findByAuthority("ADMIN");

    }

    // TESTS:
    //--------------------------------------------------------------------------------------------------------

    @Test public void contextLoads()
    {
        AppUsers user = new AppUsers();
        user.setUsername("ivan");
        user.setPassword("1131");

        AppRoles rol = new AppRoles();
        rol.setAuthority("Admin");

        user.addRol(rol);

        appRolesRepo.save(rol); // not necessary with cascade persist/merge
        appUsersRepo.save(user);

        Collection<AppUsers> userResult = appUsersRepo.findByUsername("ivan");
        Collection<AppRoles> rolResult = appRolesRepo.findByAuthority("Admin");

        System.out.println("done");

        AppUsers deletedUser = userResult.stream().findFirst().orElse(null);

        deletedUser.removeRol(rol);
        deletedUser = appUsersRepo.save(deletedUser);

        appUsersRepo.delete(deletedUser);

        userResult = appUsersRepo.findByUsername("ivan");

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

        Group result1 = groupRepo.save(group);

        System.out.println(result1.getName());

        Collection<Group> result2 = groupRepo.findByName("Nutri Group");

        System.out.println("done");
    }

    @Test public void pim()
    {
        Collection<AppUsers> user = appUsersRepo.findByUsername("admin");
        Collection<AppRoles> role = appRolesRepo.findByAuthority("ADMIN");

        AppRoles adminRol = new AppRoles();
        adminRol.setAuthority("ADMIN");
        appRolesRepo.save(adminRol);

        AppUsers adminUser = new AppUsers();
        adminUser.setEnabled(true);
        adminUser.setUsername("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword(encoder.encode("admin"));
        adminUser.addRol(adminRol);
        appUsersRepo.save(adminUser);

        user = appUsersRepo.findByUsername("admin");
        role = appRolesRepo.findByAuthority("ADMIN");

        System.out.println("done");

    }

    @Test public void pom()
    {
        Collection<AppUsers> user = appUsersRepo.findByUsername("admin");
        Collection<AppRoles> role = appRolesRepo.findByAuthority("ADMIN");

        AppUsers adminUser = new AppUsers();
        adminUser.setEnabled(true);
        adminUser.setUsername("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword(encoder.encode("admin"));
        adminUser = appUsersRepo.save(adminUser);

        AppRoles adminRol = new AppRoles();
        adminRol.setAuthority("ADMIN");
        adminRol.addUser(adminUser);
        appRolesRepo.save(adminRol);

        user = appUsersRepo.findByUsername("admin");
        role = appRolesRepo.findByAuthority("ADMIN");

        System.out.println("done");

    }

    @Test public void restTest()
    {
        String foodString1 = "https://api.nal.usda.gov/ndb/reports/?ndbno=786631&type=b&format=xml&api_key=DEMO_KEY";
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786631?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodRepo.save(food);

        log.info("FOOD: {}", food);

        food = foodRepo.findById(food.getFdcId()).get();

        log.info("FOOD: {}", food);
    }

    @Test public void foodDBTest()
    {
        String foodString1 = "https://api.nal.usda.gov/ndb/reports/?ndbno=786631&type=b&format=xml&api_key=DEMO_KEY";
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786631?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodRepo.save(food);

        Collection<Food>foods = foodRepo.findByDescriptionIgnoreCaseLike("%Apple%");

        log.info("FOOD: {}", foods.isEmpty());
    }

}
