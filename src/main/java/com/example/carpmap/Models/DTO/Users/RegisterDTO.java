package com.example.carpmap.Models.DTO.Users;

import jakarta.validation.constraints.*;

public class RegisterDTO {

    @NotEmpty(message = "First name cannot be empty!")
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 character!")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty!")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 character!")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Email is incorrect")
    private String email;

    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 character!")
    private String username;

    @NotEmpty(message = "Password cannot be empty!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 character!")
    private String password;

    @NotEmpty(message = "ConfirmPassword cannot be empty!")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 character!")
    private String confirmPassword;

    public RegisterDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
