package com.example.carpmap.Models.DTO.Ip;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class SearchIpDTO {

    //    @NotEmpty(message = "Полето неможе да бъде празно")
//    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
//    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$",
//            message = "REGEX")
//    private String address;
    //TESTING REGEX FOR IPv4 AND IPv6
    @NotEmpty(message = "Полето неможе да бъде празно")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    @Pattern(regexp = "^((25[0-5]|(2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))(\\.(?<!$)|$)){4}$" + // IPv4
            "|^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|" +
            "([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|" +
            "([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|" +
            "([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|" +
            ":((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|" +
            "::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))\\.){3,3}" +
            "(25[0-5]|(2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))|" +
            "([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]))\\.){3,3}" +
            "(25[0-5]|(2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])))$", // IPv6,
            message = "REGEX")
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