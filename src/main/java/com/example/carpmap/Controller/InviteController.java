package com.example.carpmap.Controller;
//ONLY FOR JOKE

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InviteController {

    @GetMapping("/invite")
    public ModelAndView invite(){
        return new ModelAndView("invite");
    }
}
