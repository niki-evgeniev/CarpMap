package com.example.carpmap.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DonateController {

    @GetMapping("/donate")
    public ModelAndView donate() {
        return new ModelAndView("test");
    }
}
