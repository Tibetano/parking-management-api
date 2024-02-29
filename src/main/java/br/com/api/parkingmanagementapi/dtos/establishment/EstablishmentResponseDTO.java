package br.com.api.parkingmanagementapi.dtos.establishment;

import br.com.api.parkingmanagementapi.models.Establishment;

public record EstablishmentResponseDTO(
        String name,
        String cnpj,
        String address,
        String phoneNumber,
        Integer NumberOfCarSpaces,
        Integer NumberOfMotorcycleSpaces
) {
    public EstablishmentResponseDTO(Establishment establishment){
        this(
                establishment.getName(),
                establishment.getCnpj(),
                establishment.getAddress(),
                establishment.getPhoneNumber(),
                establishment.getNumberOfCarSpaces(),
                establishment.getNumberOfMotorcycleSpaces()
        );
    }
}
