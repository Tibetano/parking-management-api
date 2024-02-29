package br.com.api.parkingmanagementapi.dtos.user;

import br.com.api.parkingmanagementapi.enums.UserRole;

public record UserRequestDTO(
        String username,
        String password,
        String cpf,
        String phoneNumber,
        String email,
        UserRole userRole
) {
}
