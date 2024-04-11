package com.example.carpmap.Controller;

import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Service.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final IpAddressService ipAddressService;
    private final UsersService usersService;
    private int counter;

    public HomeController(IpAddressService ipAddressService, UsersService usersService) {
        this.ipAddressService = ipAddressService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails) {
        counter = counter + 1;
        System.out.println("Visiting app " + counter);
        String ipAddress = ipAddressService.getIp();
        System.out.println(ipAddress);
        if (userDetails != null){
            usersService.checkIpAddressLogin(userDetails.getUsername(), ipAddress);
        } else {
            usersService.getIpVisitor(ipAddress);
        }
        return new ModelAndView("index");
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }
}
