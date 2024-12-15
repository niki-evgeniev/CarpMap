package com.example.carpmap.Utility;


import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;
import com.example.carpmap.Service.BlogService;
import com.example.carpmap.Service.IpAddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class IpUtility {

    private final IpAddressService ipAddressService;
    private final BlogService blogService;

    public IpUtility(IpAddressService ipAddressService, BlogService blogService) {
        this.ipAddressService = ipAddressService;
        this.blogService = blogService;

    }

    public ModelAndView getIpAndBlog(UserDetails userDetails, String cloudflareIp, HttpServletRequest request) {
        String ipAddress = ipAddressService.getIp().trim();
        System.out.println(LocalDateTime.now() + " Visitor address : " + ipAddress);
        System.out.println(LocalDateTime.now() + " Visitor cloudflare address : " + cloudflareIp);

        if (userDetails != null) {
            ipAddressService.checkIpAddressWhenUserLogin(userDetails.getUsername(), cloudflareIp);
        } else {
            ipAddressService.checkIpAddressAndAddToDB(cloudflareIp);
        }
        List<BlogPackagesDTO> blogPackagesDTO = blogService.getBlogPackages();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("blogPackages", blogPackagesDTO);
        modelAndView.addObject("currentUrl", request.getRequestURI());
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        return modelAndView;
    }

    public void registerIpAddress(UserDetails userDetails, String cloudflareIp, HttpServletRequest request) {
        // login
        String ipAddress = ipAddressService.getIp().trim();
        System.out.println(LocalDateTime.now() + " Visitor address Login Form : " + ipAddress);
        System.out.println(LocalDateTime.now() + " Visitor cloudflare address Login Form : " + cloudflareIp);

        if (userDetails != null) {
            ipAddressService.checkIpAddressWhenUserLogin(userDetails.getUsername(), cloudflareIp);
        } else {
            ipAddressService.checkIpAddressAndAddToDB(cloudflareIp);
        }
    }
}
