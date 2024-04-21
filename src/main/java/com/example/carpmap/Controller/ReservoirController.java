package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.FishNameDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsEditDTO;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Service.FishService;
import com.example.carpmap.Service.ReservoirsService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirController {

    private final ReservoirsService reservoirsService;
    private final FishService fishService;

    public ReservoirController(ReservoirsService reservoirsService, FishService fishService) {
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
    }

    @DeleteMapping("{id}")
    public ModelAndView delete(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (checkForAdminRole(userDetails)) {
            reservoirsService.deleteReservoir(id);
        }
        return new ModelAndView("redirect:/reservoirs/reservoirsAll");
    }

    @GetMapping("reservoirsEdit/{id}")
    public ModelAndView reservoirsEdit(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        boolean hasRoleAdmin = checkForAdminRole(userDetails);

        if (hasRoleAdmin) {
            ReservoirsEditDTO reservoirsEditDTO = reservoirsService.findReservoirToEdit(id);
            //TODO FIX BUG
//            List<FishNameDTO> fishTypeNonExist = fishService.getNonExistingFishType(reservoirsEditDTO.getFishName());
            List<FishNameDTO> allFishName = fishService.getAllFishName();

            ModelAndView modelAndView = new ModelAndView("reservoirEdit");
            modelAndView.addObject("reservoirsEditDTO", reservoirsEditDTO);
            modelAndView.addObject("fishTypeNonExist", allFishName);
            return modelAndView;
        }
        return new ModelAndView("index");
    }

    @PostMapping("reservoirsEdit")
    public ModelAndView reservoirsEdit(@Valid ReservoirsEditDTO reservoirsEditDTO, BindingResult bindingResult) {
        System.out.println();
        Long idReservoir = reservoirsService.editReservoir(reservoirsEditDTO);
        if (idReservoir == 0L) {
            return new ModelAndView("index");
        }
        return new ModelAndView("redirect:reservoirsAll/idReservoir");
    }

    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {

        return new ReservoirsAddDTO();

    }

    private static boolean checkForAdminRole(UserDetails userDetails) {
        String roleAdmin = "ROLE_" + RoleType.ADMIN;
        boolean hasRoleAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleAdmin));
        return hasRoleAdmin;
    }
}
