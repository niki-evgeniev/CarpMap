package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Models.Entity.Fish;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Enums.ReservoirType;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Repository.FishRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.PictureService;
import com.example.carpmap.Service.ReservoirsService;
import com.example.carpmap.Utility.ConvertorBgToEn;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.carpmap.Cammon.ErrorMessages.*;
import static com.example.carpmap.Cammon.SuccessfulMessages.*;

@Service
public class ReservoirsServiceImpl implements ReservoirsService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservoirsServiceImpl.class);
    private final ModelMapper modelMapper;
    private final ReservoirRepository reservoirRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final FishRepository fishRepository;
    private final PictureService pictureService;
    private final ConvertorBgToEn convertorBgToEn;


    public ReservoirsServiceImpl(ModelMapper modelMapper, ReservoirRepository reservoirRepository,
                                 CountryRepository countryRepository, UserRepository userRepository,
                                 FishRepository fishRepository, PictureService pictureService,
                                 ConvertorBgToEn convertorBgToEn) {
        this.modelMapper = modelMapper;
        this.reservoirRepository = reservoirRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.fishRepository = fishRepository;
        this.pictureService = pictureService;
        this.convertorBgToEn = convertorBgToEn;
    }

    @Override
    public boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO, UserDetails userDetails) {
        Reservoir addNewReservoirs = modelMapper.map(reservoirsAddDTO, Reservoir.class);
        Optional<Country> country = countryRepository.findByCountry(reservoirsAddDTO.getCountry());
        List<String> pictureLink = List.of(reservoirsAddDTO.getUrlImage2(),
                reservoirsAddDTO.getUrlImage3(), reservoirsAddDTO.getUrlImage4());

        if (country.isPresent()) {
            addNewReservoirs.setCountry(country.get());
            Optional<User> findUser = userRepository.findByUsername(userDetails.getUsername());

            if (findUser.isPresent()) {
                List<Fish> fish = getFish(reservoirsAddDTO);

                addNewReservoirs.setFish(fish);
                addNewReservoirs.setUser(findUser.get());
                String createUrlName = convertorBgToEn.convertCyrillicToLatin(addNewReservoirs.getName());
                addNewReservoirs.setUrlName(createUrlName);
                reservoirRepository.save(addNewReservoirs);
                System.out.printf(SUCCESSFUL_ADD_RESERVOIR,
                        reservoirsAddDTO.getName(), reservoirsAddDTO.getCountry());

                pictureService.saveImages(pictureLink, addNewReservoirs);
                return true;
            } else {
                System.out.printf(USER_WHO_ADD_RESERVOIRS_NOT_FOUND,
                        reservoirsAddDTO.getName());
            }
        }
        System.out.printf(COUNTRY_NOT_FOUND, reservoirsAddDTO.getCountry());
        return false;
    }

    private List<Fish> getFish(ReservoirsAddDTO reservoirsAddDTO) {
        List<Fish> fish = new ArrayList<>();
        for (String fishName : reservoirsAddDTO.getFishName()) {
            Optional<Fish> findFishName = fishRepository.findByFishName(fishName);
            findFishName.ifPresent(fish::add);
            System.out.printf(SUCCESSFUL_ADD_FISH_TYPE_TO_RESERVOIR,
                    fishName, reservoirsAddDTO.getName());
        }
        return fish;
    }

    @Override
    public boolean checkNameExisting(String name) {
        Optional<Reservoir> existName = reservoirRepository.findByName(name);
        return existName.isPresent();
    }

    @Override
    public Page<ReservoirAllDTO> getAllReservoirs(Pageable pageable) {
        Page<Reservoir> findAllReservoir = reservoirRepository.findAll(pageable);
        Page<ReservoirAllDTO> allReservoirs = findAllReservoir
                .map(reservoir -> {
                    return modelMapper.map(reservoir, ReservoirAllDTO.class);
                });

        return allReservoirs;
    }

    @Override
    public Page<ReservoirAllDTO> getReservoirsByType(String type, Pageable pageable) {
        ReservoirType reservoirType = switch (type) {
            case "free_reservoir" -> ReservoirType.СВОБОДЕН;
            case "private_reservoir" -> ReservoirType.ЧАСТЕН;
            default -> null;
        };
        Page<Reservoir> allByReservoirType = null;

        if (reservoirType != null) {
            allByReservoirType = reservoirRepository.findAllByReservoirType(reservoirType, pageable);

        } else if (type.equals("countVisitors")) {
            Pageable sortedByCountVisitors = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "countVisitors"));

            allByReservoirType = reservoirRepository.findAll(sortedByCountVisitors);

        } else {
            allByReservoirType = reservoirRepository.findAll(pageable);
        }

        Page<ReservoirAllDTO> reservoirByType = allByReservoirType
                .map(reservoir -> {
                    return modelMapper.map(reservoir, ReservoirAllDTO.class);
                });

//        PRINT ALL NAME
//        List<Reservoir> all = reservoirRepository.findAll();
//        for (Reservoir reservoir : all) {
//            if (reservoir.getUrlName().isEmpty()) {
//                String urlEng = convertorBgToEn.convertCyrillicToLatin(reservoir.getName().toLowerCase());
//                reservoir.setUrlName(urlEng);
//                System.out.println(urlEng);
//            }
//            reservoirRepository.saveAll(all);
//        }
        return reservoirByType;
    }

    @Override
    public Page<ReservoirAllDTO> findReservoirByName(String reservoir, Pageable pageable) {
        Page<Reservoir> allByName = reservoirRepository.findAllByName(reservoir, pageable);
        Page<ReservoirAllDTO> allReservoirByName = allByName
                .map(reservoirByName -> {
                    return modelMapper.map(reservoirByName, ReservoirAllDTO.class);
                });
        return allReservoirByName;
    }

    @Override
    @Transactional
    public void deleteReservoir(Long id) {
        Optional<Reservoir> toDelete = reservoirRepository.findById(id);
        pictureService.deleteAllListOfPicture(id);

        if (toDelete.isPresent()) {
            reservoirRepository.deleteById(id);
            System.out.printf(SUCCESSFUL_DELETE_RESERVOIR, toDelete.get().getName());
        } else {
            System.out.print(NOT_FOUND_TO_DELETE_RESERVOIR);
        }
    }

    @Override
    public List<ReservoirEditGalleryDTO> getAllGalleryImage(Long id) {
        List<ReservoirEditGalleryDTO> allPicture = pictureService.findAllPicture(id);
        return allPicture;
    }

    @Override
    public ReservoirsDetailsDTO getDetailsByUrlName(String urlName) {
        Optional<Reservoir> findReservoir = reservoirRepository.findByUrlName(urlName);
        ReservoirsDetailsDTO reservoirsDetailsDTO = modelMapper.map(findReservoir, ReservoirsDetailsDTO.class);

        List<FishNameDTO> fihsNameList = new ArrayList<>();

        if (findReservoir.isPresent()) {
            Reservoir reservoirCount = findReservoir.get();
            if (reservoirCount.getCountVisitors() == null) {
                reservoirCount.setCountVisitors(Integer.parseInt(String.valueOf(1)));
                reservoirRepository.save(reservoirCount);
            } else {
                Integer countVisitors = reservoirCount.getCountVisitors();
                countVisitors++;
                reservoirCount.setCountVisitors(countVisitors);
                reservoirRepository.save(findReservoir.get());
            }
            System.out.printf(COUNT_RESERVOIR_OPENING,
                    reservoirCount.getName(), reservoirCount.getCountVisitors());

            List<Fish> allDetailsFishTypes = findReservoir.get().getFish();
            for (Fish fish : allDetailsFishTypes) {
                FishNameDTO mapDBFishType = new FishNameDTO();
                mapDBFishType.setFishName(fish.getFishName());
                fihsNameList.add(mapDBFishType);
            }

            String fishNames = fihsNameList.stream()
                    .map(FishNameDTO::getFishName)
                    .collect(Collectors.joining(", "));
            reservoirsDetailsDTO.setFishNames(fishNames);
        } else {
            String errMsg = String.format(RESERVOIR_WITH_ID_NOT_FOUND_REDIRECT_TO_INDEX, urlName);
            LOGGER.error(errMsg);
        }
        return reservoirsDetailsDTO;
    }

    @Override
    public ReservoirIDDTO isReservoirId(String urlName) {

        if (isNumeric(urlName)) {
            Long id = Long.parseLong(urlName);
            Optional<Reservoir> findId = reservoirRepository.findById(Long.valueOf(urlName));
            return modelMapper.map(findId, ReservoirIDDTO.class);
        }
        System.out.println("RETURNING NULL FROM isReservoirId");
        return null;
    }

    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public ReservoirsEditDTO findReservoirToEdit(Long id) {
        Optional<Reservoir> reservoirDetails = reservoirRepository.findById(id);
        ReservoirsEditDTO editingReservoir = modelMapper.map(reservoirDetails, ReservoirsEditDTO.class);

        if (reservoirDetails.isEmpty()) {
            String errMsg = String.format(RESERVOIR_WITH_ID_NOT_FOUND, id);
            LOGGER.error(errMsg);
            System.out.println("Method name: findReservoirToEdit" );
            return null;
        }

        reservoirDetails.ifPresent(reservoir -> editingReservoir.setCountry(reservoir.getCountry()
                .getCountry()));

        List<Fish> fishReservoirToEdit = reservoirDetails.get().getFish();
        List<String> fishToAdd = fishReservoirToEdit
                .stream()
                .map(Fish::getFishName)
                .toList();
        editingReservoir.setFishName(fishToAdd);

        System.out.printf(SUCCESSFUL_LOAD_RESERVOIR_TO_EDIT, editingReservoir.getName(),
                editingReservoir.getCountry(), editingReservoir.getCity(),
                editingReservoir.getReservoirType(), editingReservoir.getLatitude(),
                editingReservoir.getLongitude());

        return editingReservoir;
    }

    @Override
    public String editReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails) {
        Optional<Reservoir> findReservoir = reservoirRepository.findById(reservoirsEditDTO.getId());

        if (findReservoir.isPresent()) {
            if (!reservoirsEditDTO.getName().equals(findReservoir.get().getName())) {
                boolean isExistNameOfReservoir = checkNameExisting(reservoirsEditDTO.getName());
                if (isExistNameOfReservoir) {
                    return "existing name";
                }
            }
            Reservoir editReservoir = editingReservoir(reservoirsEditDTO, userDetails, findReservoir);
            reservoirRepository.save(editReservoir);
            System.out.printf(SUCCESSFUL_EDIT_RESERVOIR, editReservoir.getName(),
                    editReservoir.getCountry().getCountry(), editReservoir.getCity(),
                    editReservoir.getReservoirType(), editReservoir.getLatitude(),
                    editReservoir.getLongitude());
            return editReservoir.getUrlName();
        }
        return "";
    }

    private Reservoir editingReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails, Optional<Reservoir> findReservoir) {
        Reservoir editReservoir = modelMapper.map(reservoirsEditDTO, Reservoir.class);

        if (findReservoir.isPresent()) {
            editReservoir.setCountry(findReservoir.get().getCountry());
            editReservoir.setCountVisitors(findReservoir.get().getCountVisitors());
            editReservoir.setFish(findReservoir.get().getFish());
            String urlName = convertorBgToEn.convertCyrillicToLatin(reservoirsEditDTO.getName().toLowerCase());
            editReservoir.setUrlName(urlName);
            LocalDateTime createDate = findReservoir.get().getCreateDate();
            editReservoir.setCreateDate(createDate);
        }

        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        user.ifPresent(editReservoir::setUser);
        editReservoir.setModifiedDate(LocalDateTime.now());

        if (!editReservoir.getCountry().getCountry().equals(reservoirsEditDTO.getCountry())) {
            Optional<Country> setNewCountry = countryRepository.findByCountry(reservoirsEditDTO.getCountry());
            setNewCountry.ifPresent(editReservoir::setCountry);
        }

        if (editReservoir.getFish().size() != (reservoirsEditDTO.getFishName().size())) {
            List<Fish> listAllFish = new ArrayList<>();
            for (String fishName : reservoirsEditDTO.getFishName()) {
                Optional<Fish> byFishName = fishRepository.findByFishName(fishName);
                byFishName.ifPresent(listAllFish::add);
            }
            editReservoir.setFish(listAllFish);
        }
        return editReservoir;
    }
}
