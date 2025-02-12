package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Service.CountryService;
import com.example.carpmap.Service.FishService;
import com.example.carpmap.Service.ReservoirsService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirController {

    private final ReservoirsService reservoirsService;
    private final FishService fishService;
    private final CountryService countryService;

    public ReservoirController(ReservoirsService reservoirsService, FishService fishService,
                               CountryService countryService) {
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
        this.countryService = countryService;
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (checkForAdminRole(userDetails)) {
            reservoirsService.deleteReservoir(id);
        }
        return new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
    }

    @GetMapping("reservoirsEdit/{id}")
    public ModelAndView reservoirsEdit(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        boolean hasRoleAdmin = checkForAdminRole(userDetails);

        if (hasRoleAdmin) {
            ReservoirsEditDTO reservoirsEditDTO = reservoirsService.findReservoirToEdit(id);
            if (reservoirsEditDTO == null) {
                return new ModelAndView("errors/errorFindPage");
            }
            List<FishNameDTO> allFishName = fishService.getAllFishName();
            List<CountryDTO> allCountry = countryService.getAllCountry();

            ModelAndView modelAndView = new ModelAndView("reservoirEdit");
            modelAndView.addObject("reservoirsEditDTO", reservoirsEditDTO);
            modelAndView.addObject("fishTypeNonExist", allFishName);
            modelAndView.addObject("allCountry", allCountry);
            return modelAndView;
        }
        return new ModelAndView("index");
    }

    @PostMapping("reservoirsEdit/{id}")
    public ModelAndView reservoirsEdit(@Valid ReservoirsEditDTO reservoirsEditDTO, BindingResult bindingResult,
                                       @AuthenticationPrincipal UserDetails userDetails) throws UnsupportedEncodingException {
        boolean nameExisting = false;

        if (!bindingResult.hasErrors()) {
            String urlName = reservoirsService.editReservoir(reservoirsEditDTO, userDetails);
            if (urlName.isEmpty()) {
                return new ModelAndView("errors/errorFindPage");
            } else if (urlName.equals("existing name")) {
                nameExisting = true;
            } else {
                String name = reservoirsEditDTO.getName();
                return new ModelAndView("redirect:/reservoirs/" + urlName);
            }

        }
        List<FishNameDTO> allFishName = fishService.getAllFishName();
        List<CountryDTO> allCountry = countryService.getAllCountry();
        ModelAndView modelAndView = new ModelAndView("reservoirEdit");
        modelAndView.addObject("fishTypeNonExist", allFishName);
        modelAndView.addObject("nameExisting", nameExisting);
        modelAndView.addObject("allCountry", allCountry);

        return modelAndView;
    }

    @GetMapping("gallery/{id}")
    public ModelAndView editGallery(@PathVariable("id") Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        boolean hasRoleAdmin = checkForAdminRole(userDetails);
        if (hasRoleAdmin) {
            List<ReservoirEditGalleryDTO> ReservoirEditGalleryDTO = reservoirsService.getAllGalleryImage(id);
            ModelAndView modelAndView = new ModelAndView("reservoirEditGallery");
            modelAndView.addObject("idRes", id);
            modelAndView.addObject("reservoirEditGalleryDTO", ReservoirEditGalleryDTO);

            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("reservoirEditGallery");
        return modelAndView;
    }

    @PostMapping("gallery/edit/{id}")
    public ModelAndView reservoirsEdit(@PathVariable("id") Long id,
                                       @Valid EditGalleryDTO editGalleryDTO, BindingResult bindingResult,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView modelAndView = new ModelAndView("redirect:/reservoirs/");
        return modelAndView;
    }

    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {
        return new ReservoirsAddDTO();
    }

    @ModelAttribute
    ReservoirEditGalleryDTO reservoirEditGalleryDTO() {
        return new ReservoirEditGalleryDTO();
    }

    @ModelAttribute
    ReservoirPostGalleryDTO reservoirPostGalleryDTO() {
        return new ReservoirPostGalleryDTO();
    }

    @ModelAttribute
    EditGalleryDTO galleryDTO() {
        return new EditGalleryDTO();
    }

    private static boolean checkForAdminRole(UserDetails userDetails) {
        String roleAdmin = "ROLE_" + RoleType.ADMIN;
        boolean hasRoleAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleAdmin));
        return hasRoleAdmin;
    }
}
