package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Mail.MailDetailsDTO;
import com.example.carpmap.Models.DTO.Mail.MailSendDTO;
import com.example.carpmap.Service.MailService;
import com.example.carpmap.Service.MailjetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MailController {

    private final MailService mailService;
    private final MailjetService mailjetService;

    public MailController(MailService mailService, MailjetService mailjetService) {
        this.mailService = mailService;
        this.mailjetService = mailjetService;
    }

    @GetMapping("/mail")
    public ModelAndView mail(@PageableDefault(size = 9, sort = {"isRead", "id"}, direction = Sort.Direction.ASC)
                             Pageable pageable) {

        Page<MailDetailsDTO> allContactMail = mailService.getAllContactMail(pageable);
        long countAllMail = mailService.countAllMail();
        long countNewMail = mailService.countAllNewMail();

        ModelAndView modelAndView = new ModelAndView("mail");
        modelAndView.addObject("allContact", allContactMail);
        modelAndView.addObject("countAllMail", countAllMail);
        modelAndView.addObject("countNewMail", countNewMail);
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

    @GetMapping("/sendMail")
    public ModelAndView getSendMail() {
        return new ModelAndView("mailSend");
    }

    @PostMapping("/sendMail")
    public ModelAndView sendMail(@Valid MailSendDTO mailSendDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("message/message");
        String message = "";
        if (!bindingResult.hasErrors()) {
            String sendEmail = mailjetService.sendEmail(mailSendDTO);
            switch (sendEmail) {
                case "SUCCESS":
                    message = "Успешно изпратихте имейл";
                    System.out.println("Успешно изпратихте имейл на " + mailSendDTO.getToEmail());
                    break;
                case "ERROR":
                    message = "Невалиден имейл";
                    break;
                case "Error in mailjet server":
                    message = "Има проблем с изпращането на емайли";
                    break;
            }
            modelAndView.addObject("message", message);
            return modelAndView;
        }
        return new ModelAndView("mailSend");
    }

    @GetMapping("/testMail")
    public ModelAndView testMailMsg(){
        ModelAndView modelAndView = new ModelAndView("message/message");
        modelAndView.addObject("message", "Има проблем с изпращането на емайли");
        return modelAndView;
    }
}
