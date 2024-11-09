package com.example.carpmap.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fish/")
public class FishController {


    @GetMapping("fishing-type")
    public ModelAndView getFish(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("fish");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        return modelAndView;
    }
}
