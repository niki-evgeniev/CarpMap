package com.example.carpmap.init;

import com.example.carpmap.Models.Entity.IpAddress;
import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddToDb implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UsersService usersService;
    private final CountryService countryService;
    private final FishService fishService;
    private final BlogService blogService;
    private final IpAddressRepository ipAddressRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AddToDb.class);

    public AddToDb(UserRoleService userRoleService, UsersService usersService,
                   CountryService countryService, FishService fishService, BlogService blogService,
                   IpAddressRepository ipAddressRepository) {
        this.userRoleService = userRoleService;
        this.usersService = usersService;
        this.countryService = countryService;
        this.fishService = fishService;
        this.blogService = blogService;
        this.ipAddressRepository = ipAddressRepository;
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
