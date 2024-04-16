package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Service.ReservoirsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirController {

    private final ReservoirsService reservoirsService;

    public ReservoirController(ReservoirsService reservoirsService) {
        this.reservoirsService = reservoirsService;
    }

    @DeleteMapping("{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
//       reservoirsService.deleteReservoir(id);
        return new ModelAndView("redirect:/reservoirs/reservoirsAll");
    }

    @GetMapping("reservoirsEdit/{id}")
    public ModelAndView reservoirsEdit(@PathVariable("id") Long id) {
        ReservoirsAddDTO reservoirsAddDTO = reservoirsService.findReservoirToEdit(id);
        ModelAndView modelAndView = new ModelAndView("reservoirsAdd");
        modelAndView.addObject("reservoirsAddDTO", reservoirsAddDTO);
        return modelAndView;
    }
    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {
        return new ReservoirsAddDTO();
    }
}
