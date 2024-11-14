package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Service.FishListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final FishListService fishListService;

    public FishController( FishListService fishListService) {
        this.fishListService = fishListService;
    }


    @GetMapping("fishing-type")
    public ModelAndView getFish(@PageableDefault(size = 20) Pageable pageable,
                                HttpServletRequest request) {

        Page<FishListAllDTO> getAllFishList = fishListService.getAll(pageable);
        ModelAndView modelAndView = new ModelAndView("fish");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        modelAndView.addObject("allFishList", getAllFishList);
        return modelAndView;
    }

    @GetMapping("add/adding-fish")
    public ModelAndView addFish() {

        ModelAndView modelAndView = new ModelAndView("fishAdd");

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
