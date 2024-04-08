package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Service.CountryService;
import com.example.carpmap.Service.FishService;
import com.example.carpmap.Service.ReservoirsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirsController {

    private final CountryService countryService;
    private final ReservoirsService reservoirsService;
    private final FishService fishService;


    public ReservoirsController(CountryService countryService, ReservoirsService reservoirsService,
                                FishService fishService) {
        this.countryService = countryService;
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
    }


    @GetMapping("reservoirsAll")
    public ModelAndView reservoirsAll(
            @PageableDefault(size = 6, sort = "name") Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("reservoirs");
        Page<ReservoirAllDTO> allReservoir = reservoirsService.getAllReservoirs(pageable);
        modelAndView.addObject("allReservoir", allReservoir);
        return modelAndView;
    }

    @GetMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd() {

        ModelAndView modelAndView = getAllCountry();
        List<FishNameDTO> fishNamesDTOS = fishService.getAllFishName();
        modelAndView.addObject("fishNames", fishNamesDTOS);
        return modelAndView;
    }

    @PostMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd(@Valid ReservoirsAddDTO reservoirsAddDTO,
                                      BindingResult bindingResult) {
        boolean isExistNameOfReservoir = reservoirsService.checkNameExisting(reservoirsAddDTO.getName());
        if (!bindingResult.hasErrors() && !isExistNameOfReservoir) {
            boolean isAddedReservoirs = reservoirsService.addReservoirs(reservoirsAddDTO);
            if (isAddedReservoirs) {
                return new ModelAndView("redirect:/");
            }
        } else {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }
        ModelAndView modelAndView = getAllCountry();
        modelAndView.addObject("isExistNameOfReservoir", isExistNameOfReservoir);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView details(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("reservoirsDetails");

        ReservoirsDetailsDTO reservoirsDetailsDTO = reservoirsService.getDetails(id);

        modelAndView.addObject("details", reservoirsDetailsDTO);

        return modelAndView;
    }

    private ModelAndView getAllCountry() {
        ModelAndView modelAndView = new ModelAndView("reservoirsAdd");
        List<CountryDTO> allCountry = countryService.getAllCountry();
        modelAndView.addObject("allCountry", allCountry);
        return modelAndView;
    }

    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {
        return new ReservoirsAddDTO();
    }
}
