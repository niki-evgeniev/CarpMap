package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        String req = request.getHeader("referer");
        if (!bindingResult.hasErrors()) {
            String reservoirName = searchDTO.getReservoir().trim();
            Page<ReservoirAllDTO> reservoirByName = reservoirsService.findReservoirByName(reservoirName, pageable);
            ModelAndView modelAndView = new ModelAndView("reservoirs");
            modelAndView.addObject("allReservoir", reservoirByName);
            return modelAndView;
        }

        if (req.contains("/reservoirs/reservoirsByType")){
            String type = req;
            Pattern pattern = Pattern.compile("/reservoirs/reservoirsByType/(.+)");
            Matcher matcher = pattern.matcher(type);
            if (matcher.find()){
                type = matcher.group(1);
                System.out.println(type);
            }

            ModelAndView modelAndView = new ModelAndView("reservoirs");
            Page<ReservoirAllDTO> allReservoirByType = reservoirsService.getReservoirsByType(type, pageable);
            if (allReservoirByType == null) {
                return new ModelAndView("errors/errorFindPage404");
            }
            modelAndView.addObject("allReservoir", allReservoirByType);
            modelAndView.addObject("type", type);
            modelAndView.addObject("currentUrl", request.getRequestURI());
            String navbarTransparent = "navbar";
            modelAndView.addObject("navbar", navbarTransparent);
            System.out.println("Reservoir type opening");
            return modelAndView;
        }

        String cloudflareIp = request.getRemoteAddr();
        ModelAndView modelAndView = ipUtility.getAllIndexInfo(userDetails, cloudflareIp, request);
        System.out.println("search type opening");
        return modelAndView;
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}

