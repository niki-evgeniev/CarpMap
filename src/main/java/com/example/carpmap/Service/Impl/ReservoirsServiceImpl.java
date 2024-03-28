package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.Exception.ObjectNotFoundException;
import com.example.carpmap.Service.ReservoirsService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
//        Country country = countryRepository.findByCountry(reservoirsAddDTO.getCountry()).orElseThrow(
//                () -> new ObjectNotFoundException("Country: " + reservoirsAddDTO.getCountry() + " Not Found"));
        Optional<Country> country = countryRepository.findByCountry(reservoirsAddDTO.getCountry());
        if (country.isPresent()) {
            addNewReservoirs.setCountry(country.get());
            Optional<User> findUser = userRepository.findById(1L);
            if (findUser.isPresent()) {
                addNewReservoirs.setUser(findUser.get());
                reservoirRepository.save(addNewReservoirs);
                System.out.printf("Success added reservoir: %s %n country: %s",
                        reservoirsAddDTO.getName(), reservoirsAddDTO.getCountry());
                return true;
            } else {
                System.out.printf("User %s who add reservoirs not found", reservoirsAddDTO.getName());
            }
        }
        System.out.printf("Country with name: %s not found", reservoirsAddDTO.getCountry());
        return false;
    }
}
