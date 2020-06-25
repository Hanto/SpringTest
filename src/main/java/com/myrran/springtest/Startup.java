package com.myrran.springtest;

import com.myrran.springtest.model.appusers.AppRoles;
import com.myrran.springtest.model.appusers.AppRolesRepo;
import com.myrran.springtest.model.appusers.AppUsers;
import com.myrran.springtest.model.appusers.AppUsersRepo;
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
    private final AppUsersRepo appUsersRepo;
    private final AppRolesRepo appRolesRepo;
    private @Autowired RestTemplate restTemplate;
    private @Autowired PasswordEncoder encoder;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Startup(AppUsersRepo userRepo, AppRolesRepo appRolesRepo)
    {   this.appUsersRepo = userRepo; this.appRolesRepo = appRolesRepo; }

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
        Collection<AppRoles> role = appRolesRepo.findByAuthority("ADMIN");

        if (role.isEmpty() && user.isEmpty())
        {
            AppRoles adminRol = new AppRoles();
            adminRol.setAuthority("ADMIN");
            adminRol = appRolesRepo.save(adminRol);

            AppUsers adminUser = new AppUsers();
            adminUser.setEnabled(true);
            adminUser.setUsername("admin");
            adminUser.setEnabled(true);
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.addRol(adminRol);
            appUsersRepo.save(adminUser);
        }

        user = appUsersRepo.findByUsername("admin");
        role = appRolesRepo.findByAuthority("ADMIN");

        user.forEach(appUsers -> log.info("User: " + appUsers.getUsername()));
        user.forEach(appUsers -> log.info("User: " + appUsers.getPassword()));
        role.forEach(appRoles -> log.info("Rol: " + appRoles.getAuthority()));
    }
}