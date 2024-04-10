package com.example.carpmap.init;

import com.example.carpmap.Service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddToDb implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UsersService usersService;
    private final CountryService countryService;
    private final FishService fishService;
    private final BlogService blogService;

    public AddToDb(UserRoleService userRoleService, UsersService usersService,
                   CountryService countryService, FishService fishService, BlogService blogService) {
        this.userRoleService = userRoleService;
        this.usersService = usersService;
        this.countryService = countryService;
        this.fishService = fishService;
        this.blogService = blogService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.addRoleIfNotExist();
        countryService.addFirstCountry();
        usersService.addAdminIfNotExist();
        fishService.addFishType();
        blogService.addBlog();
    }
}
