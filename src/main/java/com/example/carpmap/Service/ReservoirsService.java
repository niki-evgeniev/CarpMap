package com.example.carpmap.Service;

import com.example.carpmap.Models.DTO.Reservoirs.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ReservoirsService {

    boolean addReservoirs(ReservoirsAddDTO reservoirsAddDTO, UserDetails userDetails);

    boolean checkNameExisting(String name);

    ReservoirsEditDTO findReservoirToEdit(Long id);

    String editReservoir(ReservoirsEditDTO reservoirsEditDTO, UserDetails userDetails);

    Page<ReservoirAllDTO> getAllReservoirs(Pageable pageable);

    Page<ReservoirAllDTO> getReservoirsByType(String type, Pageable pageable);

    Page<ReservoirAllDTO> searchReservoirs(String reservoir, Pageable pageable);

    void deleteReservoir(Long id);

    List<ReservoirEditGalleryDTO> getAllGalleryImage(Long id);

    ReservoirsDetailsDTO getDetailsByUrlName(String urlName);

    ReservoirIDDTO isReservoirId(String urlName);
}
