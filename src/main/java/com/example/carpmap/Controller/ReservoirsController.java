package com.example.carpmap.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reservoirs/")
public class ReservoirsController {


    @GetMapping("viewAll")
    public ModelAndView reservoirs(){
        return new ModelAndView("reservoirs");
    }
    @GetMapping("reservoirsAdd")
    public ModelAndView reservoirsAdd(){
        return new ModelAndView("reservoirsAdd");
    }
}
