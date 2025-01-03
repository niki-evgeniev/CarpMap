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
import java.util.Random;

@Component
public class IpUtility {

    private final IpAddressService ipAddressService;
    private final BlogService blogService;
    private final List<String> videos = List.of(
            "/video/20220501_114816.mp4", "/video/20220501_144620.mp4", "/video/20220501_144659.mp4",
            "/video/20220501_184913.mp4", "/video/20220521_185040.mp4", "/video/20220521_201710.mp4",
            "/video/20220521_210015.mp4", "/video/20220521_224800.mp4", "/video/20220522_133751.mp4",
            "/video/20220522_133939.mp4", "/video/20220522_155950.mp4",
            "/video/20220501_154929.mp4", "/video/20220501_115313.mp4");
    private String lastVideoUrl = "";

    public IpUtility(IpAddressService ipAddressService, BlogService blogService) {
        this.ipAddressService = ipAddressService;
        this.blogService = blogService;

    }

    public ModelAndView getAllIndexInfo(UserDetails userDetails, String cloudflareIp, HttpServletRequest request) {
        String ipAddress = ipAddressService.getIp().trim();
        System.out.println(LocalDateTime.now() + " Visitor address : " + ipAddress);
        System.out.println(LocalDateTime.now() + " Visitor cloudflare address : " + cloudflareIp);

        if (userDetails != null) {
            ipAddressService.checkIpAddressWhenUserLogin(userDetails.getUsername(), cloudflareIp);
        } else {
            ipAddressService.checkIpAddressAndAddToDB(cloudflareIp);
        }
        List<BlogPackagesDTO> blogPackagesDTO = blogService.getBlogPackages();
        String randomVideo = getRandomVideo();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("blogPackages", blogPackagesDTO);
        modelAndView.addObject("currentUrl", request.getRequestURI());
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        modelAndView.addObject("videoPath", randomVideo);
        return modelAndView;
    }

    private String getRandomVideo() {
        Random random = new Random();
        String videoUrl = videos.get(random.nextInt(videos.size()));

        while (lastVideoUrl.equals(videoUrl)) {
            videoUrl = videos.get(random.nextInt(videos.size()));
        }
        lastVideoUrl = videoUrl;
        return videoUrl;
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
