package com.example.carpmap.Models.DTO.Fish;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SearchFishDTO {
    @NotEmpty(message = "Въведете име на язовир")
    @Size(min = 3, max = 50, message = "Може да въвеждате максимално 50 символа и минимално 3")
    private String name;

    public SearchFishDTO() {
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
