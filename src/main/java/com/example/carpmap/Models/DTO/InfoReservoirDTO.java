package com.example.carpmap.Models.DTO;

import java.time.LocalDate;

public class InfoReservoirDTO {

    private String name;

    private String uuid;

    private LocalDate addedDate;

    private String totalVolume;

    private String minimumFlowVolume;

    private String fillPercentage;

    private String availableVolume;

    private String volumePercentage;

    private String allAvailableVolume;

    private double inflow_m3s = 5.44;

    private double outflow_m3s = 2450.4;

    public InfoReservoirDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getMinimumFlowVolume() {
        return minimumFlowVolume;
    }

    public void setMinimumFlowVolume(String minimumFlowVolume) {
        this.minimumFlowVolume = minimumFlowVolume;
    }

    public String getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(String fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public String getAvailableVolume() {
        return availableVolume;
    }

    public void setAvailableVolume(String availableVolume) {
        this.availableVolume = availableVolume;
    }

    public String getVolumePercentage() {
        return volumePercentage;
    }

    public void setVolumePercentage(String volumePercentage) {
        this.volumePercentage = volumePercentage;
    }

    public String getAllAvailableVolume() {
        return allAvailableVolume;
    }

    public void setAllAvailableVolume(String allAvailableVolume) {
        this.allAvailableVolume = allAvailableVolume;
    }

    public double getInflow_m3s() {
        return inflow_m3s;
    }

    public void setInflow_m3s(double inflow_m3s) {
        this.inflow_m3s = inflow_m3s;
    }

    public double getOutflow_m3s() {
        return outflow_m3s;
    }

    public void setOutflow_m3s(double outflow_m3s) {
        this.outflow_m3s = outflow_m3s;
    }
}
