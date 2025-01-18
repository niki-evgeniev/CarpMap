package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Fish.FishListAllDTO;
import com.example.carpmap.Models.DTO.Fish.SearchFishDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.FishListService;
import com.example.carpmap.Service.FishService;
import com.example.carpmap.Service.ReservoirsService;
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

    public SearchController(ReservoirsService reservoirsService, IpUtility ipUtility,
                            GetReservoirView getReservoirView, FishListService fishListService) {
        this.reservoirsService = reservoirsService;
        this.ipUtility = ipUtility;
        this.getReservoirView = getReservoirView;
        this.fishListService = fishListService;
    }


    @GetMapping("/search")
    public ModelAndView search(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid SearchDTO searchDTO, BindingResult bindingResult,
                               @PageableDefault(size = 9, sort = "name") Pageable pageable,
                               HttpServletRequest request) throws InterruptedException {

        ModelAndView modelAndView1 = getSearchReservoir(searchDTO, bindingResult, pageable);
        if (modelAndView1 != null) return modelAndView1;
        String cloudflareIp = request.getRemoteAddr();
        ModelAndView modelAndView = ipUtility.getAllIndexInfo(userDetails, cloudflareIp, request);
        System.out.println("search type opening");
        return modelAndView;
    }

    @GetMapping("/searchReservoir")
    public ModelAndView searchReservoir(HttpServletRequest request,
                                        @Valid SearchDTO searchDTO, BindingResult bindingResult,
                                        @PageableDefault(size = 9, sort = "name") Pageable pageable) {
        ModelAndView modelAndView = getSearchReservoir(searchDTO, bindingResult, pageable);
        if (modelAndView != null) {
            return modelAndView;
        }
        String req = request.getHeader("referer");
        if (req.contains("/reservoirs/reservoirsByType")) {
            String type = req;
            Pattern pattern = Pattern.compile("/reservoirs/reservoirsByType/(.+)");
            Matcher matcher = pattern.matcher(type);
            if (matcher.find()) {
                type = matcher.group(1);
                System.out.println(type);
            }
            return getReservoirView.getReservoirs(type, pageable, request);
        }
        return new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
    }

    @GetMapping("/searchFishType")
    public ModelAndView searchFishType(@Valid SearchFishDTO searchFishDTO, BindingResult bindingResult,
                                       @PageableDefault(size = 9, sort = "name") Pageable pageable,
                                       HttpServletRequest request) {
        if (!bindingResult.hasErrors()) {
            String fishType = searchFishDTO.getFishType().trim();
            Page<FishListAllDTO> searchFish = fishListService.searchFish(fishType, pageable);
            ModelAndView modelAndView = new ModelAndView("fish");
            modelAndView.addObject("currentUrl", request.getRequestURI());
            modelAndView.addObject("allFishList", searchFish);
            String navbarTransparent = "navbar";
            modelAndView.addObject("navbar", navbarTransparent);
            System.out.println("fishList type opening");
            return modelAndView;
        }
        return new ModelAndView("fish");
    }

    private ModelAndView getSearchReservoir(SearchDTO searchDTO, BindingResult bindingResult, Pageable pageable) {
        if (!bindingResult.hasErrors()) {
            String reservoirName = searchDTO.getReservoir().trim();
            Page<ReservoirAllDTO> reservoirByName = reservoirsService.findReservoirByName(reservoirName, pageable);
            ModelAndView modelAndView = new ModelAndView("reservoirs");
            modelAndView.addObject("allReservoir", reservoirByName);
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

