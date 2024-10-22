package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.ProfileAllDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileEditDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileInfoDTO;
import com.example.carpmap.Models.DTO.Profile.ProfileNewPasswordDTO;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Service.ProfileService;
import com.example.carpmap.Service.ServerInfoService;
import com.example.carpmap.Utility.AppInfo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final ProfileService profileService;
    private final RestTemplate restTemplate;
    private final SystemInfo systemInfo;
    private final ServerInfoService serverInfoService;
    private final IpAddressService ipAddressService;
    private final AppInfo appInfo;


    public AdminController(ProfileService profileService, RestTemplate restTemplate,
                           SystemInfo systemInfo, ServerInfoService serverInfoService,
                           IpAddressService ipAddressService, AppInfo appInfo) {
        this.profileService = profileService;
        this.restTemplate = restTemplate;
        this.systemInfo = systemInfo;
        this.serverInfoService = serverInfoService;
        this.ipAddressService = ipAddressService;
        this.appInfo = appInfo;
    }

    @GetMapping("details/byId/{id}")
    public ModelAndView detailsById(@PathVariable("id") Long id) {

        String activeTab = "profile-overview";
        ProfileInfoDTO profileInfoDTO = profileService.findProfileById(id);
        ProfileEditDTO map = profileService.mapInfoDtoToEditDTO(profileInfoDTO);
        ProfileNewPasswordDTO profileNewPasswordDTO = new ProfileNewPasswordDTO();
        profileNewPasswordDTO.setId(profileInfoDTO.getId());

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("profileInfoDTO", profileInfoDTO);
        modelAndView.addObject("profileEditDTO", map);
        modelAndView.addObject("profileNewPasswordDTO", profileNewPasswordDTO);
        modelAndView.addObject("activeTab", activeTab);
        return modelAndView;
    }

    @GetMapping("profiles")
    public ModelAndView profiles(
            @PageableDefault(size = 9, sort = "id") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("adminAllProfiles");
        Page<ProfileAllDTO> allProfiles = profileService.findAllUsers(pageable);
        modelAndView.addObject("allProfiles", allProfiles);
        return modelAndView;
    }

    @GetMapping("server-info")
    public ModelAndView getServerInfo(Model model) {
        String health = restTemplate.getForObject("http://localhost:8080/actuator/health", String.class);
        String metric = restTemplate.getForObject("http://localhost:8080/actuator/metrics", String.class);

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long usedMemory = totalMemory - freeMemory;

        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        String cpu = centralProcessor.getProcessorIdentifier().getName();
        double cpuLoad = centralProcessor.getSystemCpuLoad(1000) * 100;
        String formattedCpuLoad = String.format("%.2f", cpuLoad);
        String uptime = serverInfoService.getUptime();
        String v = appInfo.getAppVersion();
        System.out.println();
        Long counter = ipAddressService.findAllVisits();

        Long countLastDayVisitor = ipAddressService.findLastDayVisitor();
        Long newUserForToday = ipAddressService.findNewUsersForToday();

        ModelAndView modelAndView = new ModelAndView("serverInfo");
        modelAndView.addObject("freeMemory", freeMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("usedMemory", usedMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("totalMemory",  totalMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("cpuLoad",  formattedCpuLoad + " %");
        modelAndView.addObject("cpu",  cpu);
        modelAndView.addObject("countLastDayVisitor",  countLastDayVisitor);
        modelAndView.addObject("counter",  counter);
        modelAndView.addObject("newUserForToday",  newUserForToday);
        modelAndView.addObject("v",  v);

        modelAndView.addObject("uptime", uptime);

        return modelAndView;
    }

}
