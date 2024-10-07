package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Service.MailService;
import com.example.carpmap.Service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class MailController {

    private final ProfileService profileService;
    private final MailService mailService;

    public MailController(ProfileService profileService, MailService mailService) {
        this.profileService = profileService;
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public ModelAndView mail(@PageableDefault(size = 9, sort = "id") Pageable pageable) {

        Page<MailDetailsDTO> allContactMail = mailService.getAllContactMail(pageable);

        ModelAndView modelAndView = new ModelAndView("mail");
        modelAndView.addObject("allContact", allContactMail);
        return modelAndView;
    }
}
