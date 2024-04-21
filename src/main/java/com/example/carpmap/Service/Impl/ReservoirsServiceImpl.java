package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import com.example.carpmap.Models.Entity.*;
import com.example.carpmap.Repository.*;
import com.example.carpmap.Service.PictureService;
import com.example.carpmap.Service.ReservoirsService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PictureRepository pictureRepository;

    public ReservoirsServiceImpl(ModelMapper modelMapper, ReservoirRepository reservoirRepository,
                                 CountryRepository countryRepository, UserRepository userRepository,
                                 FishRepository fishRepository, PictureService pictureService, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.reservoirRepository = reservoirRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.fishRepository = fishRepository;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO) {
        Reservoir addNewReservoirs = modelMapper.map(reservoirsAddDTO, Reservoir.class);
        Optional<Country> country = countryRepository.findByCountry(reservoirsAddDTO.getCountry());
        List<String> pictureLink = List.of(reservoirsAddDTO.getUrlImage2(),
                reservoirsAddDTO.getUrlImage3(), reservoirsAddDTO.getUrlImage4());

        if (country.isPresent()) {
            addNewReservoirs.setCountry(country.get());
            Optional<User> findUser = userRepository.findById(1L);

            if (findUser.isPresent()) {
                List<Fish> fish = new ArrayList<>();
                for (String fishName : reservoirsAddDTO.getFishName()) {
                    Optional<Fish> findFishName = fishRepository.findByFishName(fishName);
                    findFishName.ifPresent(fish::add);
                    System.out.printf(SUCCESSFUL_ADD_FISH_TYPE_TO_RESERVOIR,
                            fishName, reservoirsAddDTO.getName());
                }

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
        returnInfoForEdit.setCountry(editReservoirDetails.get().getCountry().getCountry());
        List<Fish> fishReservoirToEdit = editReservoirDetails.get().getFish();
        List<String> fishToAdd = new ArrayList<>();
        for (Fish f : fishReservoirToEdit) {
            fishToAdd.add(f.getFishName());
        }
        returnInfoForEdit.setFishName(fishToAdd);
        System.out.printf(SUCCESSFUL_LOAD_RESERVOIR_TO_EDIT, returnInfoForEdit.getName(), returnInfoForEdit.getCountry(), returnInfoForEdit.getCity(),
                returnInfoForEdit.getReservoirType(), returnInfoForEdit.getLatitude(), returnInfoForEdit.getLongitude());


        return returnInfoForEdit;
    }

    @Override
    public Long editReservoir(ReservoirsEditDTO reservoirsEditDTO) {
        Optional<Reservoir> editReservoir = reservoirRepository.findById(reservoirsEditDTO.getId());

        if (editReservoir.isPresent()) {

            //map
            if (!editReservoir.get().getCountry().getCountry().equals(reservoirsEditDTO.getCountry())) {
                Optional<Country> setNewCountry = countryRepository.findByCountry(reservoirsEditDTO.getCountry());
                editReservoir.get().setCountry(setNewCountry.get());
            }
            editReservoir.get().setCity(reservoirsEditDTO.getCity());
            editReservoir.get().setName(reservoirsEditDTO.getName());
            editReservoir.get().setLatitude(reservoirsEditDTO.getLatitude());
            editReservoir.get().setLongitude(reservoirsEditDTO.getLongitude());
            editReservoir.get().setMainUrlImage(reservoirsEditDTO.getMainUrlImage());
            editReservoir.get().setInformation(reservoirsEditDTO.getInformation());
            editReservoir.get().setDescription(reservoirsEditDTO.getDescription());

            if (editReservoir.get().getFish().size() != (reservoirsEditDTO.getFishName().size())) {
                List<Fish> listAllFish = new ArrayList<>();
                for (String fishName : reservoirsEditDTO.getFishName()) {
                    Optional<Fish> byFishName = fishRepository.findByFishName(fishName);
                    listAllFish.add(byFishName.get());
                }
                editReservoir.get().setFish(listAllFish);
            }
            editReservoir.get().setModifiedDate(LocalDateTime.now());
            editReservoir.get().setReservoirType(reservoirsEditDTO.getReservoirType());
            reservoirRepository.save(editReservoir.get());

            System.out.printf(SUCCESSFUL_EDIT_RESERVOIR, editReservoir.get().getName(),
                    editReservoir.get().getCountry().getCountry(), editReservoir.get().getCity(),
                    editReservoir.get().getReservoirType(), editReservoir.get().getLatitude(),
                    editReservoir.get().getLongitude());
            return reservoirsEditDTO.getId();
        }
        return 0L;
    }

}
