package com.myrran.springtest;

import com.myrran.springtest.model.Roles;
import com.myrran.springtest.model.Users;
import com.myrran.springtest.model.repo.RolesRepository;
import com.myrran.springtest.model.repo.UsersRepository;
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
    private final UsersRepository usersRepo;
    private final RolesRepository rolesRepo;
    private @Autowired RestTemplate restTemplate;
    private @Autowired PasswordEncoder encoder;

    // BUILDER:
    //--------------------------------------------------------------------------------------------------------

    @Autowired public Startup(UsersRepository userRepo, RolesRepository rolesRepo)
    {   this.usersRepo = userRepo; this.rolesRepo = rolesRepo; }

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

        //initAdmin();
    }

    public void initAdmin()
    {
        Collection<Users> user = usersRepo.findByUsername("admin");
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