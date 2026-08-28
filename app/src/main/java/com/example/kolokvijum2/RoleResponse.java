package com.example.kolokvijum2;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RoleResponse {
    @SerializedName("roles")
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
