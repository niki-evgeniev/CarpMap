package com.example.carpmap.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/packages")
    public ModelAndView login(){
        return new ModelAndView("packages");
    }
}
