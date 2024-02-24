package br.com.api.parkingmanagementapi.models;

import br.com.api.parkingmanagementapi.dtos.establishment.EstablishmentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {
    private String name;
    private String cnpj;
    private String address;
    private String phone;
    private Integer numberOfCarSpaces;
    private Integer numberOfMotorcycleSpaces;
    private Instant createdAt;
    private List<ParkingRecordModel> parkingsRecords;

    public Establishment(EstablishmentModel establishmentModel){
        this.name = establishmentModel.getName();
        this.cnpj = establishmentModel.getCnpj();
        this.address = establishmentModel.getAddress();
        this.phone = establishmentModel.getPhone();
        this.numberOfCarSpaces = establishmentModel.getNumberOfCarSpaces();
        this.numberOfMotorcycleSpaces = establishmentModel.getNumberOfMotorcycleSpaces();
    }

    public Establishment(EstablishmentRequestDTO establishmentRequestDTO){
        this.name = establishmentRequestDTO.name();
        this.cnpj = establishmentRequestDTO.cnpj();
        this.address = establishmentRequestDTO.address();
        this.phone = establishmentRequestDTO.phone();
        this.numberOfCarSpaces = establishmentRequestDTO.numberOfCarSpaces();
        this.numberOfMotorcycleSpaces = establishmentRequestDTO.numberOfMotorcycleSpaces();
    }
}
