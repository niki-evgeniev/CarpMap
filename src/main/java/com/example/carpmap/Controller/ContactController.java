package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.ContactDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {


    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @PostMapping("/contact")
    public ModelAndView contact(@Valid ContactDTO contactDTO, BindingResult bindingResult) {

        System.out.println();
        return new ModelAndView("index");
    }

    @ModelAttribute
    ContactDTO contactDTO() {
        return new ContactDTO();
    }
}
