package br.com.api.parkingmanagementapi.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private String role;

    private UserRole(String role){
        this.role = role;
    }
}

