package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Utility.IpUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final IpUtility ipUtility;
    private final IpAddressService ipAddressService;


    public HomeController(IpUtility ipUtility, IpAddressService ipAddressService) {
        this.ipUtility = ipUtility;
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails,
                              HttpServletRequest request) throws InterruptedException {
        String cloudflareIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        System.out.println(userAgent);
        System.out.println("Home type opening");
        return ipUtility.getAllIndexInfo(userDetails, cloudflareIp, request);
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }

    @GetMapping("/about")
    public ModelAndView about(HttpServletRequest request) {
        ipAddressService.checkIpAddressAndAddToDB(request.getRemoteAddr());
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        return modelAndView;
    }

    @GetMapping("/cookiePolicy")
    public ModelAndView cookiePolicy() {
        return new ModelAndView("cookiePolicy");
    }

}
