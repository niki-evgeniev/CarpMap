package com.example.carpmap.Models.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ip_address")
public class IpAddress extends BaseEntity {

    @Column(name = "ip_address", nullable = false, unique = true)
    private String address;

    @Column(name = "time_to_add", nullable = false)
    private LocalDateTime timeToAdd;

    @Column(name = "count_visits")
    private Long countVisits;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @Column(name = "is_banned")
    private Boolean isBanned = false;

    @ManyToOne
    private User user;

    public IpAddress() {
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

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
