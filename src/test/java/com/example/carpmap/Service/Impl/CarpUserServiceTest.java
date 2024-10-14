package com.example.carpmap.Service.Impl;


import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarpUserServiceTest {

    private CarpUserService carpUserService;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        carpUserService = new CarpUserService(mockUserRepository);
    }

    @Test
    void testMock() {
        User user = new User();
        user.setFirstName("carp");
        user.setLastName("map");
        user.setEmail("carpmap@online");
        user.setUsername("carpmap");

        when(mockUserRepository.findByEmail("carpmap@online"))
                .thenReturn(Optional.of(user));

        Optional<User> entity = mockUserRepository.findByEmail("carpmap@online");

        Assertions.assertEquals("carp", entity.get().getFirstName());
        Assertions.assertEquals("map", entity.get().getLastName());
        Assertions.assertEquals("carpmap@online", entity.get().getEmail());
        Assertions.assertEquals("carpmap", entity.get().getUsername());
    }

    @Test
    void userNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> carpUserService.loadUserByUsername("carpmap"));
    }

    @Test
    void userFound() {
        User user = createUser();
        when(mockUserRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = carpUserService.loadUserByUsername(user.getUsername());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(user.getUsername(), userDetails.getUsername(),
                "Username name error");


    }

    private static User createUser() {
        UserRole adminRole = new UserRole();
        adminRole.setRoleType(RoleType.ADMIN);
        UserRole moderatorRole = new UserRole();
        moderatorRole.setRoleType(RoleType.MODERATOR);
        UserRole userRole = new UserRole();
        userRole.setRoleType(RoleType.USER);

        User user = new User();
        user.setFirstName("carp");
        user.setLastName("map");
        user.setEmail("carpmap@online");
        user.setUsername("carpmap");
        user.setFacebook("fb");
        user.setPassword("test");
        user.setRoles(List.of(adminRole,userRole,moderatorRole));
        return user;
    }
}
