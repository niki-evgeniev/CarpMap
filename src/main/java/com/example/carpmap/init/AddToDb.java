package com.example.carpmap.init;

import com.example.carpmap.Service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddToDb implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UsersService usersService;
    private final CountryService countryService;
    private final ReservoirsService reservoirsService;
    private final FishService fishService;

    public AddToDb(UserRoleService userRoleService, UsersService usersService,
                   CountryService countryService, ReservoirsService reservoirsService, FishService fishService) {
        this.userRoleService = userRoleService;
        this.usersService = usersService;
        this.countryService = countryService;
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.addRoleIfNotExist();
        countryService.addFirstCountry();
        usersService.addAdminIfNotExist();
        fishService.addFishType();
    }
}
