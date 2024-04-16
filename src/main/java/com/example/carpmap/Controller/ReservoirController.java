package com.example.carpmap.Controller;

import com.example.carpmap.Service.ReservoirsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
