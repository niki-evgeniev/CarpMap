package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.DTO.Fish.SearchFishDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.FishListService;
import com.example.carpmap.Service.ReservoirsService;
import com.example.carpmap.Utility.GetFishView;
import com.example.carpmap.Utility.GetReservoirView;
import com.example.carpmap.Utility.IpUtility;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class SearchController {

    private final ReservoirsService reservoirsService;
    private final IpUtility ipUtility;
    private final GetReservoirView getReservoirView;
    private final FishListService fishListService;
    private final GetFishView getFishView;

    public SearchController(ReservoirsService reservoirsService, IpUtility ipUtility,
                            GetReservoirView getReservoirView, FishListService fishListService,
                            GetFishView getFishView) {
        this.reservoirsService = reservoirsService;
        this.ipUtility = ipUtility;
        this.getReservoirView = getReservoirView;
        this.fishListService = fishListService;
        this.getFishView = getFishView;
    }


    @GetMapping("/search")
    public ModelAndView search(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid SearchDTO searchDTO, BindingResult bindingResult,
                               @PageableDefault(size = 9, sort = "name") Pageable pageable,
                               HttpServletRequest request) throws InterruptedException {

        ModelAndView modelAndView1 = getSearchingReservoirOrFish(searchDTO, bindingResult, pageable, request);
        if (modelAndView1 != null) {
            return modelAndView1;
        }
        String cloudflareIp = request.getRemoteAddr();
        ModelAndView modelAndView = ipUtility.getAllIndexInfo(userDetails, cloudflareIp, request);
        System.out.println("search type opening by search");
        return modelAndView;
    }

    @GetMapping("/searchReservoir")
    public ModelAndView searchReservoir(HttpServletRequest request,
                                        @Valid SearchDTO searchDTO, BindingResult bindingResult,
                                        @PageableDefault(size = 9, sort = "name") Pageable pageable) {
        ModelAndView modelAndView = getSearchingReservoirOrFish(searchDTO, bindingResult, pageable, request);
        if (modelAndView != null) {
            return modelAndView;
        }
        String req = request.getHeader("referer");
        if (req != null && req.contains("/reservoirs/reservoirsByType")) {
            String type = req;
            Pattern pattern = Pattern.compile("/reservoirs/reservoirsByType/(.+)");
            Matcher matcher = pattern.matcher(type);
            if (matcher.find()) {
                type = matcher.group(1);
                System.out.println(type);
            }
            System.out.println("search type opening by search reservoir");
            return getReservoirView.getReservoirs(type, pageable, request);
        }
        return new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
    }

    @GetMapping("/searchFishName")
    public ModelAndView searchFishType(@Valid SearchFishDTO searchFishDTO, BindingResult bindingResult,
                                       @PageableDefault(size = 12, sort = "name") Pageable pageable,
                                       HttpServletRequest request) {
        Page<FishListAllDTO> getSearchingFish = fishListService.searchFish(searchFishDTO.getName(), pageable);
        System.out.println("search type opening by search fish name");
        return getFishView.getFish(pageable, request, getSearchingFish);
    }

    private ModelAndView getSearchingReservoirOrFish(SearchDTO searchDTO, BindingResult bindingResult, Pageable pageable,
                                                     HttpServletRequest request) {
        if (!bindingResult.hasErrors()) {
            String reservoirName = searchDTO.getReservoir().trim();
            Page<ReservoirAllDTO> searchReservoirs = reservoirsService.searchReservoirs(reservoirName, pageable);
            if (searchReservoirs.getContent().isEmpty()) {
                Page<FishListAllDTO> fishListAllDTOS = fishListService.searchFish(reservoirName, pageable);
                return getFishView.getFish(pageable, request, fishListAllDTOS);
            }
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/searchReservoir") || requestURI.contains("/search")) {
                requestURI = "/reservoirs/reservoirsByType";
            }
            ModelAndView modelAndView = new ModelAndView("reservoirs");
            modelAndView.addObject("allReservoir", searchReservoirs);
            modelAndView.addObject("currentUrl", requestURI);
            String navbarTransparent = "navbar";
            modelAndView.addObject("navbar", navbarTransparent);
            return modelAndView;
        }
        return null;
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }

    @ModelAttribute
    SearchFishDTO searchFish() {
        return new SearchFishDTO();
    }
}

