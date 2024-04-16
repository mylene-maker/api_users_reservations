package com.humanbooster.authentification;


import com.humanbooster.authentification.models.Role;
import com.humanbooster.authentification.models.User;
import com.humanbooster.authentification.services.RoleService;
import com.humanbooster.authentification.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DataLoaderApplication {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {

        SpringApplication.run(DataLoaderApplication.class);
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, RoleService roleService){
        return args->{
          Role roleAdmin = roleService.findByName("ADMIN");
          Role roleUser =  roleService.findByName("USER");

            if(roleAdmin == null){
                roleAdmin = new Role("ADMIN");
                roleService.saveRole(roleAdmin);
            }

            if(roleUser == null){
                roleUser = new Role("USER");
                roleService.saveRole(roleUser);
            }

            if (userService.findByEmail("mylene_said@hotmail.fr")==null){
                User user = new User();
                user.setFirstname("Mylène");
                user.setLastname("SAID OUSSENI");
                user.setPassword("azerty123");
                user.setEmail("mylene_said@hotmail.fr");
                user.setStreet_number(26);
                user.setStreet_name("Boulevard des crabes");
                user.setZip_code("63000");
                user.setCountry("FRANCE");

                user.addRole(roleUser);
                user.addRole(roleAdmin);

                userService.saveUser(user);
            }

            if (userService.findByEmail("raissasaid@outlook.com")==null){
                User user2 = new User();
                user2.setFirstname("Raissa");
                user2.setLastname("SAID OUSSENI");
                user2.setPassword("azerty123");
                user2.setEmail("raissasaid@outlook.com");
                user2.setStreet_number(1);
                user2.setStreet_name("Avenue de Lune");
                user2.setZip_code("75500");
                user2.setCountry("FRANCE");

                user2.addRole(roleUser);

                userService.saveUser(user2);
            }

            if (userService.findByEmail("mymilabel@live.fr")==null){
                User user3 = new User();
                user3.setFirstname("Mathilda");
                user3.setLastname("RAMIREZ");
                user3.setPassword("azerty123");
                user3.setEmail("mymilabel@live.fr");
                user3.setStreet_number(96);
                user3.setStreet_name("Rue soley");
                user3.setZip_code("75500");
                user3.setCountry("ESPAGNE");

                user3.addRole(roleUser);

                userService.saveUser(user3);
            }

            if (userService.findByEmail("mymilabel@gmail.com")==null){
                User user4 = new User();
                user4.setFirstname("Boem-Soeh");
                user4.setLastname("YANG");
                user4.setPassword("azerty123");
                user4.setEmail("mymilabel@gmail.com");
                user4.setStreet_number(9);
                user4.setStreet_name("Impasse des Grues");
                user4.setZip_code("69001");
                user4.setCountry("CHINE");

                user4.addRole(roleUser);

                userService.saveUser(user4);
            }
          };
        }
    }
