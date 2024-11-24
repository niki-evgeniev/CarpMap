package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Ip.AllIpDTO;
import com.example.carpmap.Models.DTO.Ip.SearchIpDTO;
import com.example.carpmap.Service.IpAddressService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IpAddressController {

    private final IpAddressService ipAddressService;

    public IpAddressController(IpAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/admin/ip/{type}")
    public ModelAndView showByType(@PageableDefault(size = 15, sort = {"id"}) Pageable pageable,
                                   @PathVariable("type") String type) {
        ModelAndView modelAndView = new ModelAndView("ipAddress");
        Page<AllIpDTO> allIps = switch (type) {
            case "findByUser" -> ipAddressService.findOnlyUsedByUser(pageable, type);
            case "thirtyDaysAgo" -> ipAddressService.findThirtyDaysAgo(pageable, type);
            case "lastDay" -> ipAddressService.findLastDay(pageable, type);
            case "newForToday" -> ipAddressService.findNewForToday(pageable, type);
            case "banned" -> ipAddressService.findAllBanned(pageable, type);
            default -> ipAddressService.getAllIpsAddress(pageable);
        };

        modelAndView.addObject("allIps", allIps);
        modelAndView.addObject("type", type);

        return modelAndView;
    }

    @GetMapping("/admin/search-ip")
    public ModelAndView searchIp(@Valid SearchIpDTO searchIpDTO, BindingResult bindingResult,
                                 @PageableDefault(size = 9, sort = {"id"}) Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("ipAddress");
        String type = "all";
        modelAndView.addObject("type", type);
        if (!bindingResult.hasErrors()) {
            Page<AllIpDTO> searchByIpAddress = ipAddressService.findByIpAddress(pageable, searchIpDTO);
            modelAndView.addObject("allIps", searchByIpAddress);
        } else {
            Page<AllIpDTO> getAllIpAddress = ipAddressService.getAllIpsAddress(pageable);
            modelAndView.addObject("allIps", getAllIpAddress);
        }
        return modelAndView;
    }

    @ModelAttribute
    SearchIpDTO searchIpDTO() {
        return new SearchIpDTO();
    }

    @PostMapping("/admin/ban-ip/{id}")
    public ModelAndView banIp(@PathVariable("id") Long id) {
        boolean isBanned = ipAddressService.banIp(id);
        return getModelAndView(isBanned);
    }

    @PostMapping("/admin/unban-ip/{id}")
    public ModelAndView unbanIp(@PathVariable("id") Long id) {
        boolean isBanned = ipAddressService.unbanIp(id);
        return getModelAndView(isBanned);
    }

    private static ModelAndView getModelAndView(boolean isBanned) {
        if (isBanned) {
            return new ModelAndView("redirect:/admin/ip/all");
        }
        return new ModelAndView("errors/errorFindPage");
    }
}
