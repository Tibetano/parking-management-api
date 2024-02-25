package br.com.api.parkingmanagementapi.dtos.parkingRecord;

import jakarta.validation.constraints.NotBlank;

public record ParkingRecordRequestDTO(
        @NotBlank(message = "O CNPJ do estabelecimento deve ser informado!")
        String cnpj,
        @NotBlank(message = "A placa do veículo deve ser informada!")
        String plate
) {

}
