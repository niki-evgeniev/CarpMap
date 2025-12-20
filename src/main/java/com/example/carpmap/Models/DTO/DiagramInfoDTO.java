package com.example.carpmap.Models.DTO;

import java.time.LocalDate;

public class DiagramInfoDTO {

    private LocalDate date;
    private String reservoirName;
    private Double  totalVolume;
    private Double usefulVolume;

    public DiagramInfoDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReservoirName() {
        return reservoirName;
    }

    public void setReservoirName(String reservoirName) {
        this.reservoirName = reservoirName;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Double getUsefulVolume() {
        return usefulVolume;
    }

    public void setUsefulVolume(Double usefulVolume) {
        this.usefulVolume = usefulVolume;
    }
}
