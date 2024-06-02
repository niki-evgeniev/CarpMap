package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.BlogService;
import com.example.carpmap.Service.IpAddressService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    private final IpAddressService ipAddressService;
    private final BlogService blogService;


    public HomeController(IpAddressService ipAddressService, BlogService blogService) {
        this.ipAddressService = ipAddressService;
        this.blogService = blogService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {

        Long counter = ipAddressService.findAllVisits();

        if (counter == null) {
            counter = 1L;
        } else {
            counter = counter + 1L;

        }
        System.out.println("Total visitors in app " + counter);
        String ipAddress = ipAddressService.getIp().trim();
        System.out.println(LocalDateTime.now() + " Visitor address : " + ipAddress);

        if (userDetails != null) {
            ipAddressService.checkIpAddressLogin(userDetails.getUsername(), ipAddress);
        } else {
            ipAddressService.getIpVisitor(ipAddress);
//            Thread.sleep(500);
        }

        List<BlogPackagesDTO> blogPackagesDTO = blogService.getBlogPackages();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("blogPackages", blogPackagesDTO);
        modelAndView.addObject("counter", counter);
        return modelAndView;
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}
