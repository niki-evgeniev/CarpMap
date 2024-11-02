package com.example.carpmap.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnnouncedController {


    @GetMapping("/announced")
    public ModelAndView getAnnounced(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("announced");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        return modelAndView;
    }
}
