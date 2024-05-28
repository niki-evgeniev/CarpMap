package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.SearchDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {


    @PostMapping("/search")
    public ModelAndView search(@Valid SearchDTO searchDTO, BindingResult bindingResult) {
        System.out.println();

        return new ModelAndView();
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}
