package com.example.carpmap.Models.DTO;

public class TestDTO {

    private String name;

    private String login;

    private String back;

    public TestDTO() {
    }

    public String getBack() {
        return back;
    }

    public TestDTO setBack(String back) {
        this.back = back;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public TestDTO setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestDTO setName(String name) {
        this.name = name;
        return this;
    }
}
