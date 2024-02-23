package br.com.api.parkingmanagementapi.enums;

import lombok.Getter;

@Getter
public enum VehicleType {
    CAR("car"),
    MOTORCYCLE("motorcycle");

    private String type;

    private VehicleType(String type){
        this.type = type;
    }
}
