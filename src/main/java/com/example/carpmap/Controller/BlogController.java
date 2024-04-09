package com.example.carpmap.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {



    @GetMapping("/blog")
    public ModelAndView blog(){
        return new ModelAndView("blog");
    }
}
