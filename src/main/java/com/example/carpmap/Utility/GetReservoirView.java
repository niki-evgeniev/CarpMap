package com.example.carpmap.Utility;


import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Service.ReservoirsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GetReservoirView {

    private final ReservoirsService reservoirsService;

    public GetReservoirView(ReservoirsService reservoirsService) {
        this.reservoirsService = reservoirsService;
    }

    public ModelAndView getReservoirs(String type, Pageable pageable, HttpServletRequest request ) {
        ModelAndView modelAndView = new ModelAndView("reservoirs");
        Page<ReservoirAllDTO> allReservoirByType = reservoirsService.getReservoirsByType(type, pageable);
        if (allReservoirByType == null) {
            return new ModelAndView("errors/errorFindPage404");
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/searchReservoir") || requestURI.contains("/search")) {
            requestURI = "/reservoirs/reservoirsByType";
        }
        modelAndView.addObject("allReservoir", allReservoirByType);
        modelAndView.addObject("type", type);
        modelAndView.addObject("currentUrl", requestURI);
        String navbarTransparent = "navbar";
        modelAndView.addObject("navbar", navbarTransparent);
        System.out.println("Reservoir type opening");
        return modelAndView;
    }
}
