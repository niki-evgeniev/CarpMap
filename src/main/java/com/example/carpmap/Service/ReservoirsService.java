package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;

public interface ReservoirsService {

    boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO, UserDetails userDetails);

    boolean checkNameExisting(String name);

    ReservoirsEditDTO findReservoirToEdit(Long id);

    Long editReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails);

    Page<ReservoirAllDTO> getAllReservoirs(Pageable pageable);

    ReservoirsDetailsDTO getDetails(Long id);

    void deleteReservoir(Long id);



}
