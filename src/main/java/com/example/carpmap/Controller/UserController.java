package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Users.ErrorRegister;
import com.example.carpmap.Models.DTO.Users.RegisterDTO;
import com.example.carpmap.Service.UsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.carpmap.Cammon.UsersMessages.ERROR_LOGIN;

@Controller
@RequestMapping("/users/")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("login")
    public ModelAndView login() {
        System.out.println("LOGIN type opening");
        return new ModelAndView("login");
    }

    @GetMapping("register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("register")
    public ModelAndView register(@Valid RegisterDTO registerDTO,
                                 BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            List<ErrorRegister> registerUsersErrors = usersService.registerNewUser(registerDTO);

            if (!registerUsersErrors.isEmpty()) {
                ModelAndView modelAndView = new ModelAndView("register");
                modelAndView.addObject("registerUser", registerUsersErrors);
                return modelAndView;
            } else {
                return new ModelAndView("redirect:/");
            }
        } else {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }
        return new ModelAndView("register");
    }

    @RequestMapping("/login-error")
    public ModelAndView errorLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("bad_credentials", true);
        System.out.println(ERROR_LOGIN);
        return modelAndView;
    }

    @ModelAttribute
    RegisterDTO registerDTO() {
        return new RegisterDTO();
    }
}
