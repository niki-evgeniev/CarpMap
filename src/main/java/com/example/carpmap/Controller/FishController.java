package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.Fish.AddFishDTO;
import com.example.carpmap.Models.DTO.Fish.FishDetailsDTO;
import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Service.FishListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/fish-list-type/")
public class FishController {

    private final FishListService fishListService;

    public FishController(FishListService fishListService) {
        this.fishListService = fishListService;
    }


    @GetMapping("fishing-type")
    public ModelAndView getFish(@PageableDefault(size = 12, sort = "fishName") Pageable pageable,
                                HttpServletRequest request) {

        Page<FishListAllDTO> getAllFishList = fishListService.getAll(pageable);
        ModelAndView modelAndView = new ModelAndView("fish");
        modelAndView.addObject("currentUrl", request.getRequestURI());
        modelAndView.addObject("allFishList", getAllFishList);
        System.out.println("fishList type opening");
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

        ModelAndView modelAndView = new ModelAndView("fishAdd");
        if (checkNameIsExist) {
            modelAndView.addObject("existingName", true);
        } else if (!bindingResult.hasErrors()) {
            fishListService.addFishList(addFishDTO, userDetails);
            return new ModelAndView("redirect:/fish-list-type/fishing-type");
        }
        return modelAndView;
    }

    @GetMapping("{urlName}")
    public ModelAndView getFishInformation(@PathVariable("urlName") String urlName) {
        FishDetailsDTO fishDetailsDTO = fishListService.getFishListDetails(urlName);
        ModelAndView modelAndView = new ModelAndView("fishDetails");
        modelAndView.addObject("fishDetailsDTO", fishDetailsDTO);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/{urlName}")
    public ModelAndView deleteFishListDetails(@PathVariable String urlName) {

        fishListService.deleteFishListDetails(urlName);

        System.out.println("DELETE DELETE");
        return new ModelAndView("redirect:/fish-list-type/fishing-type");
    }

    @ModelAttribute
    AddFishDTO addFishDTO() {
        return new AddFishDTO();
    }
}
