package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.ReservoirsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchController {

    private final ReservoirsService reservoirsService;

    public SearchController(ReservoirsService reservoirsService) {
        this.reservoirsService = reservoirsService;
    }


    @PostMapping("/search")
    public ModelAndView search(@Valid SearchDTO searchDTO, BindingResult bindingResult,
                               @PageableDefault(size = 6, sort = "name") Pageable pageable) {

        if (!bindingResult.hasErrors()) {
            Page<ReservoirAllDTO> reservoirByName = reservoirsService.findReservoirByName(searchDTO.getReservoir(), pageable);
            ModelAndView modelAndView = new ModelAndView("reservoirs");
            modelAndView.addObject("allReservoir", reservoirByName);
            return modelAndView;

        }

        return new ModelAndView("about");
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}
