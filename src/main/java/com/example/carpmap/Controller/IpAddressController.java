package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import com.example.carpmap.Service.IpAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IpAddressController {

    private final IpAddressService ipAddressService;

    public IpAddressController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/admin/ip")
    public ModelAndView showAllIp(@PageableDefault(size = 15, sort = {"id"}) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("ipAddress");

        Page<AllIpDTO> allIps = ipAddressService.getAllIpsAddress(pageable);
        modelAndView.addObject("allIps", allIps);

        return modelAndView;
    }
}
