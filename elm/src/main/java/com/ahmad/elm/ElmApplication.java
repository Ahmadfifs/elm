package com.ahmad.elm;

import com.ahmad.elm.model.Role;
import com.ahmad.elm.model.User;
import com.ahmad.elm.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication()
public class ElmApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElmApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder){
        return args -> {
            User admin = new User("admin" , "0500500500" , "123" , "elm@elm.com");
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setRoles(new Role("ADMIN"));
            userRepo.save(admin);
        };
    }

}
