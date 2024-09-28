package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Blog.BlogPackagesDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.BlogService;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Service.ReservoirsService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class SearchController {

    private final ReservoirsService reservoirsService;
    private final IpUtility ipUtility;

    public SearchController(ReservoirsService reservoirsService, IpUtility ipUtility) {
        this.reservoirsService = reservoirsService;
        this.ipUtility = ipUtility;
    }


    @GetMapping("/search")
    public ModelAndView search(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid SearchDTO searchDTO, BindingResult bindingResult,
                               @PageableDefault(size = 6, sort = "name") Pageable pageable,
                               HttpServletRequest request) throws InterruptedException {
        System.out.println();
        if (!bindingResult.hasErrors()) {
            Page<ReservoirAllDTO> reservoirByName = reservoirsService.findReservoirByName(searchDTO.getReservoir(), pageable);
            ModelAndView modelAndView = new ModelAndView("reservoirs");
            modelAndView.addObject("allReservoir", reservoirByName);
            return modelAndView;
        }
        String cloudflareIp = request.getRemoteAddr();
        ModelAndView modelAndView = ipUtility.getIpAndBlog(userDetails, cloudflareIp, request);
        return modelAndView;
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}

