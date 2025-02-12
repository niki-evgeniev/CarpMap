package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import com.example.carpmap.Models.DTO.ReservoirRepositoryDTO;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Service.InformationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Comparator;
import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    private final WebClient webClient;
    private final ReservoirRepository reservoirRepository;
    private final String apiUrlAddress = "http://localhost:8181/api/info";

    public InformationServiceImpl(WebClient webClient, ReservoirRepository reservoirRepository) {
        this.webClient = webClient;
        this.reservoirRepository = reservoirRepository;
    }

    @Override
    public Page<ReservoirInfoDTO> getAllInformation(int page, int size) {

        List<ReservoirInfoDTO> informationReservoir = getApiInformation();
        if (!informationReservoir.isEmpty()) {
            List<ReservoirRepositoryDTO> findAllImages = reservoirRepository.findAllNameAndImageUrl();
            for (ReservoirInfoDTO infoReservoir : informationReservoir) {
                String reservoirName = infoReservoir.getName();
                for (ReservoirRepositoryDTO image : findAllImages) {
                    if (image.getName().equals(reservoirName)) {
                        infoReservoir.setMainUrlImage(image.getMainUrlImage());
                    }
                }
                if (infoReservoir.getMainUrlImage() == null) {
                    infoReservoir.setMainUrlImage("images/reservoirImageNotFound.jpg");
                }
            }

        }
        System.out.println();
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        assert informationReservoir != null;
        int end = Math.min((start + pageable.getPageSize()), informationReservoir.size());
        List<ReservoirInfoDTO> subList = informationReservoir.subList(start, end);

        return new PageImpl<>(subList, pageable, informationReservoir.size());
    }

    @Override
    public Page<ReservoirInfoDTO> getAllInformation2(Pageable pageable) {
        List<ReservoirInfoDTO> informationReservoir = getApiInformation();
        if (!informationReservoir.isEmpty()) {
            List<ReservoirRepositoryDTO> findAllImages = reservoirRepository.findAllNameAndImageUrl();
            for (ReservoirInfoDTO infoReservoir : informationReservoir) {
                String reservoirName = infoReservoir.getName();
                for (ReservoirRepositoryDTO image : findAllImages) {
                    if (image.getName().equals(reservoirName)) {
                        infoReservoir.setMainUrlImage(image.getMainUrlImage());
                    }
                }
                if (infoReservoir.getMainUrlImage() == null) {
                    infoReservoir.setMainUrlImage("images/reservoirImageNotFound.jpg");
                }
            }
            informationReservoir.sort(Comparator.comparing(ReservoirInfoDTO::getTotalVolume).reversed());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), informationReservoir.size());
        List<ReservoirInfoDTO> subList = informationReservoir.subList(start, end);

        return new PageImpl<>(subList, pageable, informationReservoir.size());
    }

    private List<ReservoirInfoDTO> getApiInformation() {
        try {
            return webClient.get()
                    .uri(apiUrlAddress)
                    .retrieve()
                    .bodyToFlux(ReservoirInfoDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("API NOT WORK " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return List.of();
        }

    }
}
