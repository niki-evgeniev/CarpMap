package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.ContactDTO;
import com.example.carpmap.Service.ContactService;
import com.example.carpmap.Service.IpAddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private final ContactService contactService;
    private final IpAddressService ipAddressService;

    public ContactController(ContactService contactService, IpAddressService ipAddressService) {
        this.contactService = contactService;
        this.ipAddressService = ipAddressService;
    }


    @GetMapping("/contact")
    public ModelAndView contact(HttpServletRequest request) {
        ipAddressService.checkIpAddressAndAddToDB(request.getRemoteAddr());
        request.getSession(true);
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        System.out.println("contact type opening");
        return modelAndView;
    }

    @PostMapping("/contact")
    public ModelAndView contact(@Valid ContactDTO contactDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("contact");
        }
        boolean isSavedContact = contactService.saveContact(contactDTO);
        ModelAndView modelAndView = new ModelAndView("message/message");
        String message = "Успешно изпратихте запитване към Carpmap.bg. Ще ви върнем съобщение най-скоро";
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ModelAttribute
    ContactDTO contactDTO() {
        return new ContactDTO();
    }
}
