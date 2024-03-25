package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirsController {


    @GetMapping("viewAll")
    public ModelAndView reservoirs() {
        return new ModelAndView("reservoirs");
    }

    @GetMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd() {
        return new ModelAndView("reservoirsAdd");
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
