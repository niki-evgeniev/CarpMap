package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.BlogService;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Utility.IpUtility;
import jakarta.servlet.http.HttpServletRequest;
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

    private final IpUtility ipUtility;


    public HomeController(IpUtility ipUtility) {
        this.ipUtility = ipUtility;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal UserDetails userDetails,
                              HttpServletRequest request)
            throws InterruptedException {
        String cloudflareIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        System.out.println(userAgent);
        System.out.println("Home type opening");
        return ipUtility.getIpAndBlog(userDetails, cloudflareIp, request);
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }

    @GetMapping("/about")
    public ModelAndView about(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("about");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        String fishPage = "page";
        modelAndView.addObject("page", fishPage);
        return modelAndView;
    }

    @GetMapping("/cookiePolicy")
    public ModelAndView cookiePolicy() {
        return new ModelAndView("cookiePolicy");
    }

}
