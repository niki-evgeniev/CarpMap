package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.CountryService;
import com.example.carpmap.Service.FishService;
import com.example.carpmap.Service.PictureService;
import com.example.carpmap.Service.ReservoirsService;
import com.example.carpmap.Utility.GetReservoirView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final PictureService pictureService;
    private final GetReservoirView getReservoirView;

    public ReservoirsController(CountryService countryService, ReservoirsService reservoirsService,
                                FishService fishService, PictureService pictureService, GetReservoirView getReservoirView) {
        this.countryService = countryService;
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
        this.pictureService = pictureService;
        this.getReservoirView = getReservoirView;
    }

    @GetMapping("reservoirsByType/{type}")
    public ModelAndView reservoirsByType(
            @PageableDefault(size = 9, sort = "name") Pageable pageable, @PathVariable String type,
            HttpServletRequest request) {
        switch (type) {
            case "ALL" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/reservoirs");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO reservoirs");
                return modelAndView;
            }
            case "ЧАСТЕН" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/private_reservoir");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO private_reservoir");
                return modelAndView;
            }
            case "СВОБОДЕН" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/free_reservoir");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO free_reservoir");
                return modelAndView;
            }
        }

        return getReservoirView.getReservoirs(type, pageable,request);
    }

    @GetMapping("add/reservoirAdd")
    public ModelAndView reservoirsAdd() {
        ModelAndView modelAndView = getAllCountry();
        List<FishNameDTO> fishNamesDTOS = fishService.getAllFishName();
        modelAndView.addObject("fishNames", fishNamesDTOS);
        return modelAndView;
    }

    @PostMapping("add/reservoirAdd")
    public ModelAndView reservoirsAdd(@Valid ReservoirsAddDTO reservoirsAddDTO,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        boolean isExistNameOfReservoir = reservoirsService.checkNameExisting(reservoirsAddDTO.getName());

        if (!bindingResult.hasErrors() && !isExistNameOfReservoir) {
            boolean isAddedReservoirs = reservoirsService.addReservoirs(reservoirsAddDTO, userDetails);
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
        List<FishNameDTO> fishNamesDTOS = fishService.getAllFishName();
        modelAndView.addObject("fishNames", fishNamesDTOS);
        return modelAndView;
    }

    @GetMapping("{urlName}")
    public ModelAndView details(@PathVariable("urlName") String urlName) {

        ModelAndView modelAndView = new ModelAndView("reservoirsDetails");
        ReservoirsDetailsDTO reservoirsDetailsDTO = reservoirsService.getDetailsByUrlName(urlName);
        if (reservoirsDetailsDTO == null) {
            if (urlName.equals("reservoirsAll")) {
                ModelAndView modelAndView1 = new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                return modelAndView1;
            }
            ReservoirIDDTO reservoirByID = reservoirsService.isReservoirId(urlName);
            if (reservoirByID != null) {
                ModelAndView modelAndView1 = new ModelAndView("redirect:/reservoirs/" + reservoirByID
                        .getUrlName());
                modelAndView1.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : USED ID FOR RESERVOIR: " + reservoirByID.getUrlName());
                return modelAndView1;
            }
            ModelAndView modelAndView1 = new ModelAndView("errors/errorFindPage");
            modelAndView1.setStatus(HttpStatus.NOT_FOUND);
            System.out.println("CONTROLLER : RESERVOIR WITH URL NAME " + urlName + " NOT FOUND");
            return modelAndView1;
        }

        String name = reservoirsDetailsDTO.getName();
        List<ReservoirPicturesDTO> reservoirPicturesList = pictureService.getAllReservoirPictureByName(name);
        if (reservoirPicturesList == null) {
            return new ModelAndView("errors/errorFindPage");
        }
        modelAndView.addObject("details", reservoirsDetailsDTO);
        modelAndView.addObject("pictures", reservoirPicturesList);
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

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}
