package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.Entity.*;
import com.example.carpmap.Repository.*;
import com.example.carpmap.Service.PictureService;
import com.example.carpmap.Service.ReservoirsService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.carpmap.Cammon.ErrorMessages.*;
import static com.example.carpmap.Cammon.SuccessfulMessages.*;

@Service
public class ReservoirsServiceImpl implements ReservoirsService {

    private final ModelMapper modelMapper;
    private final ReservoirRepository reservoirRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final FishRepository fishRepository;
    private final PictureService pictureService;


    public ReservoirsServiceImpl(ModelMapper modelMapper, ReservoirRepository reservoirRepository,
                                 CountryRepository countryRepository, UserRepository userRepository,
                                 FishRepository fishRepository, PictureService pictureService) {
        this.modelMapper = modelMapper;
        this.reservoirRepository = reservoirRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.fishRepository = fishRepository;
        this.pictureService = pictureService;

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
        Page<ReservoirAllDTO> allReservoirs = findAllReservoir.map(reservoir -> {
            return modelMapper.map(reservoir, ReservoirAllDTO.class);
        });

        return allReservoirs;
    }

    @Override
    public ReservoirsDetailsDTO getDetails(Long id) {
        Optional<Reservoir> findReservoir = reservoirRepository.findById(id);
        ReservoirsDetailsDTO reservoirsDetailsDTO = modelMapper.map(findReservoir, ReservoirsDetailsDTO.class);
        List<FishNameDTO> fihsNameList = new ArrayList<>();

        if (findReservoir.isPresent()) {
            List<Fish> allDetailsFishTypes = findReservoir.get().getFish();
            for (Fish fish : allDetailsFishTypes) {
                FishNameDTO mapDBFishType = new FishNameDTO();
                mapDBFishType.setFishName(fish.getFishName());
                fihsNameList.add(mapDBFishType);
            }
        }
        reservoirsDetailsDTO.setFishNameDTO(fihsNameList);
        return reservoirsDetailsDTO;
    }

    @Override
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
    public ReservoirsEditDTO findReservoirToEdit(Long id) {
        Optional<Reservoir> editReservoirDetails = reservoirRepository.findById(id);
        ReservoirsEditDTO returnInfoForEdit = modelMapper.map(editReservoirDetails, ReservoirsEditDTO.class);
        editReservoirDetails.ifPresent(reservoir -> returnInfoForEdit.setCountry(reservoir.getCountry()
                                                                        .getCountry()));
        List<String> fishToAdd = new ArrayList<>();
        if (editReservoirDetails.isPresent()) {
            List<Fish> fishReservoirToEdit = editReservoirDetails.get().getFish();
            for (Fish f : fishReservoirToEdit) {
                fishToAdd.add(f.getFishName());
            }
        }
        returnInfoForEdit.setFishName(fishToAdd);
        System.out.printf(SUCCESSFUL_LOAD_RESERVOIR_TO_EDIT, returnInfoForEdit.getName(),
                returnInfoForEdit.getCountry(), returnInfoForEdit.getCity(),
                returnInfoForEdit.getReservoirType(), returnInfoForEdit.getLatitude(),
                returnInfoForEdit.getLongitude());

        return returnInfoForEdit;
    }

    @Override
    public Long editReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails) {
        Optional<Reservoir> findReservoir = reservoirRepository.findById(reservoirsEditDTO.getId());

        if (findReservoir.isPresent()) {

            Reservoir editReservoir = editingReservoir(reservoirsEditDTO, userDetails, findReservoir);

            reservoirRepository.save(editReservoir);

            System.out.printf(SUCCESSFUL_EDIT_RESERVOIR, editReservoir.getName(),
                    editReservoir.getCountry().getCountry(), editReservoir.getCity(),
                    editReservoir.getReservoirType(), editReservoir.getLatitude(),
                    editReservoir.getLongitude());
            return reservoirsEditDTO.getId();
        }
        return 0L;
    }

    private Reservoir editingReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails, Optional<Reservoir> findReservoir) {
        Reservoir editReservoir = modelMapper.map(reservoirsEditDTO, Reservoir.class);

        if (findReservoir.isPresent()){
            editReservoir.setCountry(findReservoir.get().getCountry());
            editReservoir.setFish(findReservoir.get().getFish());
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
