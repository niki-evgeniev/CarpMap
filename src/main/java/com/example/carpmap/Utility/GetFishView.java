package com.example.carpmap.Utility;

import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GetFishView {

    public ModelAndView getFish(Pageable pageable, HttpServletRequest request, Page<FishListAllDTO> getAllFishList) {
        ModelAndView modelAndView = new ModelAndView("fish");
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/searchFishName") || requestURI.contains("/search")) {
            requestURI = "/fish-list-type";
        }
        modelAndView.addObject("currentUrl", requestURI);
        modelAndView.addObject("allFishList", getAllFishList);
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        System.out.println("fishList type opening");
        return modelAndView;
    }
}
