package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Reservoirs.CountryDTO;
import com.example.carpmap.Service.CountryService;
import com.example.carpmap.Service.FishListService;
import com.example.carpmap.Service.FishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/fish-list-type/")
public class FishController {
    private final CountryService countryService;
    private final FishListService fishListService;

    public FishController(CountryService countryService, FishListService fishListService) {
        this.countryService = countryService;
        this.fishListService = fishListService;
    }


    @GetMapping("fishing-type")
    public ModelAndView getFish(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("fish");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        return modelAndView;
    }

    @GetMapping("add/adding-fish")
    public ModelAndView addFish() {
        List<CountryDTO> allCountry = countryService.getAllCountry();
        ModelAndView modelAndView = new ModelAndView("fishAdd");
        modelAndView.addObject("allCountry", allCountry);
        return modelAndView;
    }

    @PostMapping("add/adding-fish")
    public ModelAndView addFish(@Valid AddFishDTO addFishDTO,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UserDetails userDetails) {

        boolean checkNameIsExist = fishListService.checkName(addFishDTO.getFishName());
        if (!bindingResult.hasErrors() && !checkNameIsExist) {
            fishListService.addFishList(addFishDTO, userDetails);
            return new ModelAndView("redirect:/fish-list-type/fishing-type");
        }
        return new ModelAndView("fishAdd");
    }

    @ModelAttribute
    AddFishDTO addFishDTO() {
        return new AddFishDTO();
    }
}
