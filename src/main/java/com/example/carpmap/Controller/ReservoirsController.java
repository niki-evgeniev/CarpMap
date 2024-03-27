package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.DTO.Reservoirs.CountryDTO;
import com.example.carpmap.Service.CountryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirsController {

    private final CountryService countryService;

    public ReservoirsController(CountryService countryService) {
        this.countryService = countryService;
    }


    @GetMapping("viewAll")
    public ModelAndView reservoirs() {
        return new ModelAndView("reservoirs");
    }

    @GetMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd() {

        ModelAndView modelAndView = new ModelAndView("reservoirsAdd");

        List<CountryDTO> allCountry = countryService.getAllCountry();
        modelAndView.addObject("allCountry", allCountry);

        return modelAndView;
    }

    @PostMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd(@Valid ReservoirsAddDTO reservoirsAddDTO,
                                      BindingResult bindingResult) {
        System.out.println();

     if (!bindingResult.hasErrors()){
         return new ModelAndView("redirect:/");
     }
        return new ModelAndView("reservoirsAdd");
    }

    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {
        return new ReservoirsAddDTO();
    }
}
