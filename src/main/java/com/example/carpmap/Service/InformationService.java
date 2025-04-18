package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.InfoReservoirDTO;
import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InformationService {
//    Page<ReservoirInfoDTO> getAllInformation(int page, int size);

    Page<ReservoirInfoDTO> getAllInformation(Pageable pageable);

    InfoReservoirDTO getInfoReservoir(String name);
}
