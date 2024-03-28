package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsNameDTO;

import java.util.Optional;

public interface ReservoirsService {

    boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO);

    Optional<ReservoirsNameDTO> checkNameExist(String name);
}
