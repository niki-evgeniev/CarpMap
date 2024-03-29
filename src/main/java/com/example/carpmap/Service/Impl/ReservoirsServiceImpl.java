package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsNameDTO;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.ReservoirsService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.example.carpmap.Cammon.ErrorMessages.COUNTRY_NOT_FOUND;
import static com.example.carpmap.Cammon.ErrorMessages.USER_WHO_ADD_RESERVOIRS_NOT_FOUND;
import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_ADD_RESERVOIR;

@Service
public class ReservoirsServiceImpl implements ReservoirsService {

    private final ModelMapper modelMapper;
    private final ReservoirRepository reservoirRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;

    public ReservoirsServiceImpl(ModelMapper modelMapper, ReservoirRepository reservoirRepository,
                                 CountryRepository countryRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.reservoirRepository = reservoirRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO) {

        Reservoir addNewReservoirs = modelMapper.map(reservoirsAddDTO, Reservoir.class);

        Optional<Country> country = countryRepository.findByCountry(reservoirsAddDTO.getCountry());
        if (country.isPresent()) {
            addNewReservoirs.setCountry(country.get());
            Optional<User> findUser = userRepository.findById(1L);

            if (findUser.isPresent()) {
                addNewReservoirs.setUser(findUser.get());
                reservoirRepository.save(addNewReservoirs);
                System.out.printf(SUCCESSFUL_ADD_RESERVOIR,
                        reservoirsAddDTO.getName(), reservoirsAddDTO.getCountry());
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
    public Optional<ReservoirsNameDTO> checkNameExisting(String name) {

        Optional<Reservoir> existName = reservoirRepository.findByName(name);

        ReservoirsNameDTO nameReservoir = modelMapper.map(existName, ReservoirsNameDTO.class);

        return Optional.ofNullable(nameReservoir);
    }

    @Override
    public Page<ReservoirAllDTO> getAllReservoirs(Pageable pageable) {

        Page<Reservoir> findAll = reservoirRepository.findAll(pageable);
        Page<ReservoirAllDTO> all = findAll.map(reservoir -> {
            ReservoirAllDTO reservoirAllDTO = new ReservoirAllDTO();
            reservoirAllDTO.setCity(reservoir.getCity());
            reservoirAllDTO.setId(reservoir.getId());
            reservoirAllDTO.setName(reservoir.getName());
            reservoirAllDTO.setDescription(reservoir.getDescription());
            return reservoirAllDTO;
        });
        return all;
    }
}
