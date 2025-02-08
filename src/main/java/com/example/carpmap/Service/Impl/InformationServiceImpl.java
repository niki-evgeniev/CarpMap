package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.DTO.ReservoirInfoDTO;
import com.example.carpmap.Service.InformationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    private final WebClient webClient;

    public InformationServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Page<ReservoirInfoDTO> getAllInformation(int page, int size) {

        List<ReservoirInfoDTO> block = webClient.get()
                .uri("http://localhost:8181/api/info")
                .retrieve()
                .bodyToFlux(ReservoirInfoDTO.class)
                .collectList()
                .block();
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        assert block != null;
        int end = Math.min((start + pageable.getPageSize()), block.size());
        List<ReservoirInfoDTO> subList = block.subList(start, end);

        return new PageImpl<>(subList, pageable, block.size());
    }
}
