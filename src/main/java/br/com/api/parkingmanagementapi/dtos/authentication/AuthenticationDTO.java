package br.com.api.parkingmanagementapi.dtos.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "The username must be provided.")
        String username,
        @NotBlank(message = "The password must be provided.")
        String password
) {
}
