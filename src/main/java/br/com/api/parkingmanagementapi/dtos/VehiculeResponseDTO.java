package br.com.api.parkingmanagementapi.dtos;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import br.com.api.parkingmanagementapi.models.Vehicle;

import java.time.Instant;

public record VehiculeResponseDTO(
        String mark,
        String model,
        String color,
        String plate,
        VehicleType type,
        Instant createdAt
) {
    public VehiculeResponseDTO(Vehicle vehicle){
        this(
                vehicle.getMark(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getPlate(),
                vehicle.getType(),
                vehicle.getCreatedAt()
        );
    }
}
