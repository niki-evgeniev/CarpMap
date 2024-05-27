package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.SubscribeDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController {


    @PostMapping("/send")
    public ModelAndView subscribe(@RequestParam("email") String email) {

        System.out.println();
        return new ModelAndView("index");
    }

//    @ModelAttribute
//    SubscribeDTO subscribeDTO() {
//        return new SubscribeDTO();
//    }
}
