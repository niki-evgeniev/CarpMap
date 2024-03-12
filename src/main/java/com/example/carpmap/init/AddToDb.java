package com.example.carpmap.init;

import com.example.carpmap.Service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddToDb implements CommandLineRunner {

    private final UserRoleService userRoleService;

    public AddToDb(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.addRole();
    }
}
