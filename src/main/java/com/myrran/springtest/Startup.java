package com.myrran.springtest;

import com.myrran.springtest.model.appusers.AppRoles;
import com.myrran.springtest.model.appusers.AppRolesDAO;
import com.myrran.springtest.model.appusers.AppUserDAO;
import com.myrran.springtest.model.appusers.AppUsers;
import com.myrran.springtest.model.food.Food;
import com.myrran.springtest.model.food.FoodDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Slf4j
@Component
class Startup implements CommandLineRunner
{
    private final AppUserDAO appUsersRepo;
    private final AppRolesDAO appRolesDAO;
    private @Autowired RestTemplate restTemplate;
    private @Autowired PasswordEncoder encoder;
    private @Autowired
    FoodDAO foodDAO;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Startup(AppUserDAO userRepo, AppRolesDAO appRolesDAO)
    {   this.appUsersRepo = userRepo; this.appRolesDAO = appRolesDAO; }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    @Override public void run(String... strings)
    {
        log.info("Startup");

        String originLan = "en";
        String destinyLan= "es";
        String text = "Hello Friend, how are you?";

        String translate = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + originLan +
            "&tl=" + destinyLan +"&dt=t&q=" + text;

        String food = "https://api.nal.usda.gov/ndb/reports/?ndbno=01120&type=b&format=xml&api_key=DEMO_KEY";

        Object object = restTemplate.getForObject(translate, String.class);

        log.info("OBJECT: {}", object);

        initAdmin();
    }

    public void initAdmin()
    {
        Collection<AppUsers> user = appUsersRepo.findByUsername("admin");
        Collection<AppRoles> role = appRolesDAO.findByAuthority("ADMIN");

        if (role.isEmpty() && user.isEmpty())
        {
            AppRoles adminRol = new AppRoles();
            adminRol.setAuthority("ADMIN");
            adminRol = appRolesDAO.save(adminRol);

            AppUsers adminUser = new AppUsers();
            adminUser.setEnabled(true);
            adminUser.setUsername("admin");
            adminUser.setEnabled(true);
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.addRol(adminRol);
            appUsersRepo.save(adminUser);
        }

        user = appUsersRepo.findByUsername("admin");
        role = appRolesDAO.findByAuthority("ADMIN");

        user.forEach(appUsers -> log.info("User: " + appUsers.getUsername()));
        user.forEach(appUsers -> log.info("User: " + appUsers.getPassword()));
        role.forEach(appRoles -> log.info("Rol: " + appRoles.getAuthority()));

        Collection<Food>foods = foodDAO.findByNutrient("Caffeine");

        foods.forEach(food -> log.info("Foods with Caffeine: {}", food.getDescription()));

        //importFood();
    }

    private void importFood()
    {
        for (int i= 0; i<=50; i++)
        {
            String foodID = Integer.toString (786000 + i);
            String query = "https://api.nal.usda.gov/fdc/v1/food/" + foodID + "?api_key=OUfth2dJDUjjGQSDT5v1WZ2yvP84eBzTvX0aFKJ5";
            Food food = restTemplate.getForObject(query, Food.class);
            foodDAO.save(food);

            log.info("FOOD SAVED: {}", food.getDescription());
        }
    }
}