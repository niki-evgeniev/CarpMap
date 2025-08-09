package com.example.carpmap.Models.DTO;

import java.time.LocalDate;
import java.util.UUID;

public class ReservoirInfoDTO {

    private String name;

    private String urlName;

    private UUID uuid;

    private double totalVolume;

    private double minimumFlowVolume;

    private double fillPercentage;

    private double availableVolume;

    private double volumePercentage;

    private String mainUrlImage;

    private LocalDate addedDate;

    private double inflow_m3s;

    private double outflow_m3s;

    public ReservoirInfoDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getMinimumFlowVolume() {
        return minimumFlowVolume;
    }

    public void setMinimumFlowVolume(double minimumFlowVolume) {
        this.minimumFlowVolume = minimumFlowVolume;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public void setFillPercentage(double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public double getAvailableVolume() {
        return availableVolume;
    }

    public void setAvailableVolume(double availableVolume) {
        this.availableVolume = availableVolume;
    }

    public double getVolumePercentage() {
        return volumePercentage;
    }

    public void setVolumePercentage(double volumePercentage) {
        this.volumePercentage = volumePercentage;
    }

    public String getMainUrlImage() {
        return mainUrlImage;
    }

    public void setMainUrlImage(String mainUrlImage) {
        this.mainUrlImage = mainUrlImage;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
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
