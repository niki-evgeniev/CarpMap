package com.example.carpmap.Models.DTO.Ip;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AllIpDTO {

    private Long id;

    private String address;

    private LocalDateTime timeToAdd;

    private String timeToAddFormat;

    private Long countVisits;

    private LocalDateTime lastSeen;

    private String lastSeenFormat;

    private String isBanned;

    private String userId;

    public AllIpDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getTimeToAdd() {
        return timeToAdd;
    }

    public void setTimeToAdd(LocalDateTime timeToAdd) {
        this.timeToAdd = timeToAdd;
    }

    public Long getCountVisits() {
        return countVisits;
    }

    public void setCountVisits(Long countVisits) {
        this.countVisits = countVisits;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(String isBanned) {
        this.isBanned = isBanned;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeToAddFormat() {
        if (timeToAdd != null) {
            return timeToAdd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "";
    }

    public void setTimeToAddFormat(String timeToAddFormat) {
        this.timeToAddFormat = timeToAddFormat;
    }

    public String getLastSeenFormat() {
        if (lastSeen != null) {
            return lastSeen.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "";
    }

    public void setLastSeenFormat(String lastSeenFormat) {
        this.lastSeenFormat = lastSeenFormat;
    }
}
