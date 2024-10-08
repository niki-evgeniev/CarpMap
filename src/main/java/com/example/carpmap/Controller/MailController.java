package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import com.example.carpmap.Service.MailService;
import com.example.carpmap.Service.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public ModelAndView mail(@PageableDefault(size = 9, sort = {"isRead", "id"}, direction = Sort.Direction.ASC)
                             Pageable pageable) {

        Page<MailDetailsDTO> allContactMail = mailService.getAllContactMail(pageable);

        ModelAndView modelAndView = new ModelAndView("mail");
        modelAndView.addObject("allContact", allContactMail);
        return modelAndView;
    }

    @GetMapping("mail/details/{id}")
    public ModelAndView mailDetail(@PathVariable("id") Long id) {

        ModelAndView modelAndView = new ModelAndView("mailDetails");

        MailDetailsDTO mailDetailsDTO = mailService.getDetailsMail(id);
        if (mailDetailsDTO == null) {
            return new ModelAndView("errors/errorFindPage");
        }

        modelAndView.addObject("mailDetail", mailDetailsDTO);
        return modelAndView;
    }

    @DeleteMapping("contact/delete/{id}")
    @Transactional
    public ModelAndView deleteMail(@PathVariable("id") Long id) {
        boolean isDelete = mailService.deleteMail(id);
        return new ModelAndView("redirect:/mail");
    }
}
