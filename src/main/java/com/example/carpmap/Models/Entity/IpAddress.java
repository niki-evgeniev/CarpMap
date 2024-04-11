package com.example.carpmap.Models.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "ip_address")
public class IpAddress extends BaseEntity {
    private String address;

    private LocalDate timeToAdd;

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

    public LocalDate getTimeToAdd() {
        return timeToAdd;
    }

    public void setTimeToAdd(LocalDate timeToAdd) {
        this.timeToAdd = timeToAdd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
