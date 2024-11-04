package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.Profile.*;
import com.example.carpmap.Models.Enums.RoleType;
import com.example.carpmap.Service.IpAddressService;
import com.example.carpmap.Service.ProfileService;
import com.example.carpmap.Service.ServerInfoService;
import com.example.carpmap.Utility.AppInfo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.NetworkIF;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;


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

    @PostMapping("change-roles/{id}")
    public ModelAndView changeUserRoles(@Valid ProfileChangeRoleDTO profileChangeRoleDTO,
                                        @PathVariable("id") Long id,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        BindingResult bindingResult) {

        boolean checkIsAdmin = checkForAdminRole(userDetails);
        if (checkIsAdmin && !bindingResult.hasErrors()) {
            profileService.changeRoles(profileChangeRoleDTO);
        }

        return new ModelAndView("redirect:/admin/details/byId/" + id);
    }

    private static boolean checkForAdminRole(UserDetails userDetails) {
        String roleAdmin = "ROLE_" + RoleType.ADMIN;
        boolean hasRoleAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleAdmin));
        return hasRoleAdmin;
    }

    @ModelAttribute
    ProfileChangeRoleDTO profileChangeRoleDTO() {
        return new ProfileChangeRoleDTO();
    }

    @GetMapping("profiles")
    public ModelAndView profiles(
            @PageableDefault(size = 9, sort = "id") Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("adminAllProfiles");
        Page<ProfileAllDTO> allProfiles = profileService.findAllUsers(pageable, userDetails);
        modelAndView.addObject("allProfiles", allProfiles);
        return modelAndView;
    }

    @GetMapping("server-info")
    public ModelAndView getServerInfo(Model model) {
        String health = restTemplate.getForObject("http://localhost:8080/actuator/health", String.class);
        String metric = restTemplate.getForObject("http://localhost:8080/actuator/metrics", String.class);

        return getModelAndView();
    }

    private ModelAndView getModelAndView() {
        //MEMORY INFO
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long usedMemory = totalMemory - freeMemory;

        // CPU INFO
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        String cpu = centralProcessor.getProcessorIdentifier().getName();
        double cpuLoad = centralProcessor.getSystemCpuLoad(1000) * 100;
        String formattedCpuLoad = String.format("%.2f", cpuLoad);

        // UPTIME MACHINE
        String uptime = serverInfoService.getUptime();

        //APP VERSION
        String versionApp = appInfo.getAppVersion();

        //GET INFO FROM DB FOR USERs
        Long counterAllUser = ipAddressService.findAllVisits();
        Long countLastDayVisitor = ipAddressService.findLastDayVisitor();
        Long newUserForToday = ipAddressService.findNewUsersForToday();

        // GET INFO FROM DB
        Long allReservoir = serverInfoService.countAllReservoir();
        Long allUsers = serverInfoService.countAllUsers();
        Long allPictureCloudinary = serverInfoService.countAllPictureCloudinary();

        // GET DISK INFO
        File file = new File("/");
        long freeSpace = file.getFreeSpace();
        long totalDisk = file.getTotalSpace();
        long usableSpace = file.getUsableSpace();

        ModelAndView modelAndView = new ModelAndView("serverInfo");
        modelAndView.addObject("freeMemory", freeMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("usedMemory", usedMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("totalMemory", totalMemory / (1024 * 1024) + " MB");
        modelAndView.addObject("cpuLoad", formattedCpuLoad + " %");
        modelAndView.addObject("cpu", cpu);
        modelAndView.addObject("countLastDayVisitor", countLastDayVisitor);
        modelAndView.addObject("counter", counterAllUser);
        modelAndView.addObject("newUserForToday", newUserForToday);
        modelAndView.addObject("versionApp", versionApp);
        modelAndView.addObject("allReservoir", allReservoir);
        modelAndView.addObject("allUsers", allUsers);
        modelAndView.addObject("allPictureCloudinary", allPictureCloudinary);
        modelAndView.addObject("uptime", uptime);
        modelAndView.addObject("usableSpace", usableSpace / (1024 * 1024 * 1024) + " GB");
        modelAndView.addObject("freeSpace", freeSpace / (1024 * 1024 * 1024) + " GB");
        modelAndView.addObject("totalDisk", totalDisk / (1024 * 1024 * 1024) + " GB");
        //NETWORK
        NetworkIF[] networkIFS = systemInfo.getHardware().getNetworkIFs().toArray(new NetworkIF[0]);
        for (NetworkIF net : networkIFS) {
            modelAndView.addObject("networkName", net.getName());
            modelAndView.addObject("bytesSent", net.getBytesSent() / (1024 * 1024) + " MB");
            modelAndView.addObject("bytesReceived", net.getBytesRecv() / (1024 * 1024) + " MB");
        }
        return modelAndView;
    }

}
