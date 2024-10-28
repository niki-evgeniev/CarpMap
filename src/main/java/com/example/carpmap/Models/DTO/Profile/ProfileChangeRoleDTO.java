package com.example.carpmap.Models.DTO.Profile;

import com.example.carpmap.Models.Enums.RoleType;

public class ProfileChangeRoleDTO {

    private long id;

    private RoleType roleType;

    public ProfileChangeRoleDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
