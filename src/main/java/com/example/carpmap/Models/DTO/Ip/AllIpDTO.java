package com.example.carpmap.Models.DTO.Ip;


import java.time.LocalDateTime;

public class AllIpDTO {

    private Long id;

    private String address;

    private LocalDateTime timeToAdd;

    private Long countVisits;

    private LocalDateTime lastSeen;

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
}
