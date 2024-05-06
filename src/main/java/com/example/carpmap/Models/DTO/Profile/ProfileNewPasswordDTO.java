package com.example.carpmap.Models.DTO.Profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileNewPasswordDTO {

    private Long id;

    @NotEmpty(message = "old Password name field information must not be empty")
    @Size(min = 1, max = 10)
    private String currentPassword;

    @NotEmpty(message = "Password name field information must not be empty")
    @Size(min = 1, max = 10)
    private String newPassword;

    @NotEmpty(message = "Password name field information must not be empty")
    @Size(min = 1, max = 10)
    private String confirmNewPassword;

    public ProfileNewPasswordDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
