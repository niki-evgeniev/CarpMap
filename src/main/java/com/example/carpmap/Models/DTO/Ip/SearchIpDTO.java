package com.example.carpmap.Models.DTO.Ip;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class SearchIpDTO {

    @NotEmpty(message = "Полето неможе да бъде празно")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$",
            message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String address;

    public SearchIpDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}