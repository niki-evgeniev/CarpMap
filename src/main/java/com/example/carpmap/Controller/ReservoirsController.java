package com.example.carpmap.Controller;

import com.example.carpmap.Models.DTO.DiagramInfoDTO;
import com.example.carpmap.Models.DTO.InfoReservoirDTO;
import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.DTO.SearchDTO;
import com.example.carpmap.Service.*;
import com.example.carpmap.Utility.GetReservoirView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/reservoirs/")
public class ReservoirsController {

    private final CountryService countryService;
    private final ReservoirsService reservoirsService;
    private final FishService fishService;
    private final PictureService pictureService;
    private final GetReservoirView getReservoirView;
    private final IpAddressService ipAddressService;
    private final InformationService informationService;

    public ReservoirsController(CountryService countryService, ReservoirsService reservoirsService,
                                FishService fishService, PictureService pictureService,
                                GetReservoirView getReservoirView, IpAddressService ipAddressService,
                                InformationService informationService) {
        this.countryService = countryService;
        this.reservoirsService = reservoirsService;
        this.fishService = fishService;
        this.pictureService = pictureService;
        this.getReservoirView = getReservoirView;
        this.ipAddressService = ipAddressService;
        this.informationService = informationService;
    }

    @GetMapping("reservoirsByType/{type}")
    public ModelAndView reservoirsByType(
            @PageableDefault(size = 9, sort = "name") Pageable pageable, @PathVariable String type,
            HttpServletRequest request) {
        ipAddressService.checkIpAddressAndAddToDB(request.getRemoteAddr());
        switch (type) {
            case "ALL" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/reservoirs");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO reservoirs");
                return modelAndView;
            }
            case "ЧАСТЕН" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/private_reservoir");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO private_reservoir");
                return modelAndView;
            }
            case "СВОБОДЕН" -> {
                ModelAndView modelAndView = new ModelAndView(
                        "redirect:/reservoirs/reservoirsByType/free_reservoir");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : SEARCH " + type + " REDIRECT TO free_reservoir");
                return modelAndView;
            }
        }
        return getReservoirView.getReservoirs(type, pageable, request);
    }

    @GetMapping("add/reservoirAdd")
    public ModelAndView reservoirsAdd() {
        ModelAndView modelAndView = getAllCountry();
        List<FishNameDTO> fishNamesDTOS = fishService.getAllFishName();
        modelAndView.addObject("fishNames", fishNamesDTOS);
        return modelAndView;
    }

    @PostMapping("add/reservoirAdd")
    public ModelAndView reservoirsAdd(@Valid ReservoirsAddDTO reservoirsAddDTO,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        boolean isExistNameOfReservoir = reservoirsService.checkNameExisting(reservoirsAddDTO.getName());

        if (!bindingResult.hasErrors() && !isExistNameOfReservoir) {
            boolean isAddedReservoirs = reservoirsService.addReservoirs(reservoirsAddDTO, userDetails);
            if (isAddedReservoirs) {
                return new ModelAndView("redirect:/");
            }
        } else {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }

        ModelAndView modelAndView = getAllCountry();
        modelAndView.addObject("isExistNameOfReservoir", isExistNameOfReservoir);
        List<FishNameDTO> fishNamesDTOS = fishService.getAllFishName();
        modelAndView.addObject("fishNames", fishNamesDTOS);
        return modelAndView;
    }

    @GetMapping("{urlName}")
    public ModelAndView details(@PathVariable("urlName") String urlName,
                                HttpServletRequest request) {
        ipAddressService.checkIpAddressAndAddToDB(request.getRemoteAddr());
        ModelAndView modelAndView = new ModelAndView("reservoirsDetails");
        ReservoirsDetailsDTO reservoirsDetailsDTO = reservoirsService.getDetailsByUrlName(urlName);

        if (reservoirsDetailsDTO == null) {
            if (urlName.equals("reservoirsAll")) {
                ModelAndView modelAndView1 = new ModelAndView("redirect:/reservoirs/reservoirsByType/reservoirs");
                modelAndView.setStatus(HttpStatus.MOVED_PERMANENTLY);
                return modelAndView1;
            }
            ReservoirIDDTO reservoirByID = reservoirsService.isReservoirId(urlName);
            if (reservoirByID != null) {
                ModelAndView modelAndView1 = new ModelAndView("redirect:/reservoirs/" + reservoirByID
                        .getUrlName());
                modelAndView1.setStatus(HttpStatus.MOVED_PERMANENTLY);
                System.out.println("CONTROLLER : USED ID FOR RESERVOIR: " + reservoirByID.getUrlName());
                return modelAndView1;
            }
            ModelAndView modelAndView1 = new ModelAndView("errors/errorFindPage");
            modelAndView1.setStatus(HttpStatus.NOT_FOUND);
            System.out.println("CONTROLLER : RESERVOIR WITH URL NAME " + urlName + " NOT FOUND");
            return modelAndView1;
        }

        String name = reservoirsDetailsDTO.getName();
        List<ReservoirPicturesDTO> reservoirPicturesList = pictureService.getAllReservoirPictureByName(name);
        if (reservoirPicturesList == null) {
            return new ModelAndView("errors/errorFindPage");

        }
        InfoReservoirDTO infoReservoirDTO = informationService.getInfoReservoir(name);

        List<DiagramInfoDTO> diagramInfo = informationService.getDiagramInformation(name);

        if (diagramInfo != null) {
            int W = 720, H = 260, padL = 40, padR = 50, padT = 12, padB = 36;
            List<Double> total = diagramInfo.stream().map(DiagramInfoDTO::getTotalVolume).toList();
            List<Double> useful = diagramInfo.stream().map(DiagramInfoDTO::getUsefulVolume).toList();

            String pointsTotal = buildPolylinePoints(total, W, H, padL, padR, padT, padB);
            String pointsUseful = buildPolylinePoints(useful, W, H, padL, padR, padT, padB);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
            List<String> labels = diagramInfo.stream()
                    .map(d -> d.getDate().format(dateTimeFormatter))
                    .toList();

            modelAndView.addObject("name", name);
            modelAndView.addObject("diagramInfo", diagramInfo);
            modelAndView.addObject("labels", labels);
            modelAndView.addObject("pointsTotal", pointsTotal);
            modelAndView.addObject("pointsUseful", pointsUseful);
            modelAndView.addObject("W", W);
            modelAndView.addObject("H", H);
            modelAndView.addObject("padL", padL);
            modelAndView.addObject("padR", padR);
            modelAndView.addObject("padT", padT);
            modelAndView.addObject("padB", padB);

        }

        modelAndView.addObject("details", reservoirsDetailsDTO);
        modelAndView.addObject("pictures", reservoirPicturesList);
        modelAndView.addObject("info", infoReservoirDTO);
        return modelAndView;

    }

    private static String buildPolylinePoints(List<Double> values,
                                              int W, int H,
                                              int padL, int padR, int padT, int padB) {
        int n = values.size();
        if (n < 2) return "";
        double width = W - padL - padR;
        double height = H - padT - padB;
        double stepX = width / (n - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            double v = values.get(i) == null ? 0d : Math.max(0, Math.min(100, values.get(i)));
            double x = padL + i * stepX;
            double y = padT + (100 - v) * (height / 100.0); // 0% долу, 100% горе
            if (i > 0) sb.append(' ');
            sb.append(String.format(java.util.Locale.US, "%.2f,%.2f", x, y));
        }
        return sb.toString();
    }


    private ModelAndView getAllCountry() {
        ModelAndView modelAndView = new ModelAndView("reservoirsAdd");
        List<CountryDTO> allCountry = countryService.getAllCountry();
        modelAndView.addObject("allCountry", allCountry);
        return modelAndView;
    }

    @ModelAttribute
    ReservoirsAddDTO reservoirsAddDTO() {
        return new ReservoirsAddDTO();
    }

    @ModelAttribute
    SearchDTO searchDTO() {
        return new SearchDTO();
    }
}
