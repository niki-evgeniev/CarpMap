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

        if (req.contains("/reservoirs/reservoirsByType/reservoirs")){
//            return new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
            return new ModelAndView("reservoirs");
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

