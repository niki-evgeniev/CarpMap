package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface ReservoirsService {

    boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO);

    boolean checkNameExisting(String name);

    Page<ReservoirAllDTO> getAllReservoirs(Pageable pageable);

    ReservoirsDetailsDTO getDetails(Long id);

    void deleteReservoir(Long id);

    ReservoirsEditDTO findReservoirToEdit(Long id);

    Long editReservoir(ReservoirsEditDTO reservoirsEditDTO);
}
