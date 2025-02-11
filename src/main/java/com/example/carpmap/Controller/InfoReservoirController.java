package com.example.carpmap.Controller;


import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import com.example.carpmap.Service.InformationService;
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

//    @GetMapping("/info")
//    public ModelAndView getInfo(@PageableDefault(size = 10, sort = "totalVolume") Pageable pageable) {
//
//        Page<ReservoirInfoDTO> infoReservoirsDetails2 = informationService.getAllInformation2(pageable);
//        ModelAndView modelAndView = new ModelAndView("information");
//        modelAndView.addObject("infoReservoirsDetails", infoReservoirsDetails2);
//        System.out.println();
//        return modelAndView;
//    }
@GetMapping("/info")
public ModelAndView getInfo(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
    Page<ReservoirInfoDTO> infoReservoirsDetails2 = informationService.getAllInformation2(pageable);
    ModelAndView modelAndView = new ModelAndView("information");
    modelAndView.addObject("infoReservoirsDetails", infoReservoirsDetails2);
    return modelAndView;
}

}
