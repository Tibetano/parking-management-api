package br.com.api.parkingmanagementapi.dtos;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehiculeRequestDTO(
        @NotBlank(message = "The vehicle's mark must be provided.")
        String mark,
        @NotBlank(message = "The vehicle's model must be provided.")
        String model,
        @NotBlank(message = "The vehicle's color must be provided.")
        String color,
        @NotBlank(message = "The vehicle's license plate must be provided.")
        String plate,
        @NotNull(message = "The vehicle's type must be provided.")
        VehicleType type
) {

}
