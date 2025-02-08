package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import org.springframework.data.domain.Page;

public interface InformationService {
    Page<ReservoirInfoDTO> getAllInformation(int page, int size);
}
