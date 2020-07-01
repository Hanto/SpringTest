package com.myrran.springtest.test;

import com.myrran.springtest.config.AppProperties;
import com.myrran.springtest.model.appusers.AppRoles;
import com.myrran.springtest.model.appusers.AppRolesDAO;
import com.myrran.springtest.model.appusers.AppUserDAO;
import com.myrran.springtest.model.appusers.AppUsers;
import com.myrran.springtest.model.demo.Event;
import com.myrran.springtest.model.demo.Group;
import com.myrran.springtest.model.demo.GroupDAO;
import com.myrran.springtest.model.demo.User;
import com.myrran.springtest.model.food.dao.FoodDAO;
import com.myrran.springtest.model.food.dao.NutrientDAO;
import com.myrran.springtest.model.food.dtos.FoodDTO;
import com.myrran.springtest.model.food.entities.Food;
import com.myrran.springtest.model.food.entities.Nutrient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootTest
class GlobalTests
{
    private @Autowired
    AppRolesDAO appRolesDAO;
    private @Autowired
    AppUserDAO appUsersDAO;
    private @Autowired
    GroupDAO groupRepo;
    private @Autowired ModelMapper modelMapper;
    private @Autowired AppProperties properties;
    private @Autowired PasswordEncoder encoder;
    private @Autowired RestTemplate restTemplate;
    private @Autowired FoodDAO foodDAO;
    private @Autowired NutrientDAO nutrientDAO;

    // BEFORE:
    //--------------------------------------------------------------------------------------------------------

    @BeforeEach
    public void before()
    {
        Collection<AppUsers> users = appUsersDAO.findByUsername("admin");
        Collection<AppRoles> roles = appRolesDAO.findByAuthority("ADMIN");

        if (!users.isEmpty())
            users.forEach(user -> appUsersDAO.deleteById(user.getId()));

        if (!roles.isEmpty())
            roles.forEach(rol -> appRolesDAO.deleteById(rol.getId()));

        users = appUsersDAO.findByUsername("admin");
        roles = appRolesDAO.findByAuthority("ADMIN");

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

        appRolesDAO.save(rol); // not necessary with cascade persist/merge
        appUsersDAO.save(user);

        Collection<AppUsers> userResult = appUsersDAO.findByUsername("ivan");
        Collection<AppRoles> rolResult = appRolesDAO.findByAuthority("Admin");

        System.out.println("done");

        AppUsers deletedUser = userResult.stream().findFirst().orElse(null);

        deletedUser.removeRol(rol);
        deletedUser = appUsersDAO.save(deletedUser);

        appUsersDAO.delete(deletedUser);

        userResult = appUsersDAO.findByUsername("ivan");

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
        Collection<AppUsers> user = appUsersDAO.findByUsername("admin");
        Collection<AppRoles> role = appRolesDAO.findByAuthority("ADMIN");

        AppRoles adminRol = new AppRoles();
        adminRol.setAuthority("ADMIN");
        appRolesDAO.save(adminRol);

        AppUsers adminUser = new AppUsers();
        adminUser.setEnabled(true);
        adminUser.setUsername("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword(encoder.encode("admin"));
        adminUser.addRol(adminRol);
        appUsersDAO.save(adminUser);

        user = appUsersDAO.findByUsername("admin");
        role = appRolesDAO.findByAuthority("ADMIN");

        System.out.println("done");

    }

    @Test public void pom()
    {
        Collection<AppUsers> user = appUsersDAO.findByUsername("admin");
        Collection<AppRoles> role = appRolesDAO.findByAuthority("ADMIN");

        AppUsers adminUser = new AppUsers();
        adminUser.setEnabled(true);
        adminUser.setUsername("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword(encoder.encode("admin"));
        adminUser = appUsersDAO.save(adminUser);

        AppRoles adminRol = new AppRoles();
        adminRol.setAuthority("ADMIN");
        adminRol.addUser(adminUser);
        appRolesDAO.save(adminRol);

        user = appUsersDAO.findByUsername("admin");
        role = appRolesDAO.findByAuthority("ADMIN");

        System.out.println("done");

    }

    @Test public void restTest()
    {
        String foodString1 = "https://api.nal.usda.gov/ndb/reports/?ndbno=786631&type=b&format=xml&api_key=DEMO_KEY";
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786631?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodDAO.save(food);

        log.info("FOOD: {}", food);

        food = foodDAO.findById(food.getFdcId()).get();

        log.info("FOOD: {}", food);
    }

    @Test public void foodDBTest()
    {
        String foodString1 = "https://api.nal.usda.gov/ndb/reports/?ndbno=786631&type=b&format=xml&api_key=DEMO_KEY";
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786631?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodDAO.save(food);

        Collection<Food>foods = foodDAO.findByNutrientName("Protein", 1, 5);

        log.info("FOOD: {}", foods.isEmpty());
    }

    @Test public void foodToDTOTest()
    {
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786631?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodDAO.save(food);

        Food foodEntity = foodDAO.findByNutrientName("Vitamin A%", 1, 5).stream().findFirst().orElse(new Food());
        FoodDTO dto = modelMapper.map(foodEntity, FoodDTO.class);

        log.info("FOOD: {}", dto.toString());
    }

    @Test public void foodsByNutriendID()
    {
        String foodString2 = "https://api.nal.usda.gov/fdc/v1/food/786006?api_key=DEMO_KEY";

        Food food = restTemplate.getForObject(foodString2, Food.class);

        foodDAO.save(food);

        List<Long>nutriendIDs = new ArrayList<>();
        nutriendIDs.add(1093L);
        nutriendIDs.add(1005L);
        nutriendIDs.add(1008L);
        nutriendIDs.add(1089L);
        nutriendIDs.add(1186L);
        nutriendIDs.add(1265L);

        Collection<Nutrient>nutrients = nutrientDAO.findByNameLikeIgnoreCase("%P");

        Collection<Food>foods = foodDAO.findByAllNutrientID(nutriendIDs, 1, 50);
        foods.forEach(food1 -> System.out.println("RETRIVED FOODS: " + food1.getDescription()));
    }
}
