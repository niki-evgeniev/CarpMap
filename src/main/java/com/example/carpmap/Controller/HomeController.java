package com.example.carpmap.Controller;

import com.example.carpmap.Repository.IpAddressRepository;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Service.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class HomeController {

    private final IpAddressService ipAddressService;

    private Long counter = 0L;

    public HomeController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails) {

        if (counter == 0) {
            counter = ipAddressService.findAllVisits();
            if (counter == null) {
                counter = 1L;
            }

        } else {
            counter = counter + 1L;

        }
        System.out.println("Total visitors in app " + counter);
        String ipAddress = ipAddressService.getIp();
        System.out.println(LocalDateTime.now() + " Visitor address : " + ipAddress);

        if (userDetails != null) {
            ipAddressService.checkIpAddressLogin(userDetails.getUsername(), ipAddress);
        } else {
            ipAddressService.getIpVisitor(ipAddress);
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("counter", counter);
        return modelAndView;
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/gallery")
    public ModelAndView gallery() {
        return new ModelAndView("gallery");
    }
}
