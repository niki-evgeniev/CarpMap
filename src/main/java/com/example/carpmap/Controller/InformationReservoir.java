package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import com.example.carpmap.Service.InformationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InformationReservoir {

    private final InformationService informationService;

    public InformationReservoir(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/info")
    public ModelAndView getInfo() {

        Page<ReservoirInfoDTO> infoReservoirsDetails = informationService.getAllInformation(0, 10);
        int fillPercentage = 60;

        ModelAndView modelAndView = new ModelAndView("information");
        modelAndView.addObject("fillPercentage", fillPercentage);

        return modelAndView;
    }
}
