package com.example.carpmap.init;

import com.example.carpmap.Service.UserRoleService;
import com.example.carpmap.Service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddToDb implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UsersService usersService;

    public AddToDb(UserRoleService userRoleService, UsersService usersService) {
        this.userRoleService = userRoleService;
        this.usersService = usersService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.addRoleIfNotExist();
        usersService.addAdminIfNotExist();

    }
}
