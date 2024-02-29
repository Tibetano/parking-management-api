package br.com.api.parkingmanagementapi.dtos.establishment;

import jakarta.validation.constraints.Min;

public record EstablishmentRequestDTO(
        String name,
        String cnpj,
        String address,
        String phoneNumber,
        @Min(value = 0, message = "The number of car spaces can't negative.")
        Integer numberOfCarSpaces,
        @Min(value = 0, message = "The number of motorcycle spaces can't negative.")
        Integer numberOfMotorcycleSpaces
) {
}
