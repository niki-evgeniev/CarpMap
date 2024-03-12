package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import com.example.carpmap.Service.UsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users/")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid RegisterDTO registerDTO,
                                 BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            boolean registerUser = usersService.registerUser(registerDTO);
            if (registerUser) {
                return new ModelAndView("login");
            }
        }
        return new ModelAndView("register");
    }

    @ModelAttribute
    RegisterDTO registerDTO() {
        return new RegisterDTO();
    }
}
