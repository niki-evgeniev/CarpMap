package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import com.example.carpmap.Service.InformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoReservoirController {

    private final InformationService informationService;

    public InfoReservoirController(InformationService informationService) {
        this.informationService = informationService;
    }

    //    }
    @GetMapping("/info")
    public ModelAndView getInfo(@PageableDefault(size = 10, sort = "name") Pageable pageable,
                                HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Page<ReservoirInfoDTO> infoReservoirsDetails = informationService.getAllInformation2(pageable);
//        boolean isNotWorking = false;
        ModelAndView modelAndView = new ModelAndView("information");
        if (infoReservoirsDetails.isEmpty()) {
            System.err.println("API NOT RESPONDING");
        }else {
            System.out.println("API WORKING");
        }
//        modelAndView.addObject("isNotWorking", isNotWorking);
        modelAndView.addObject("infoReservoirsDetails", infoReservoirsDetails);
        modelAndView.addObject("currentUrl", requestURI);
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);

        System.out.println("Information reservoir opening");
        return modelAndView;
    }

}
