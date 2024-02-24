package br.com.api.parkingmanagementapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity(name = "establishments")
@Table(name = "establishments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotBlank(message = "The name of the establishment must be provided.")
    private String name;
    @NotBlank(message = "The cnpj of the establishment must be provided.")
    private String cnpj;
    @NotBlank(message = "The address of the establishment must be provided.")
    private String address;
    @NotBlank(message = "The phone of the establishment must be provided.")
    private String phone;
    @NotNull(message = "The number of car spaces must be informed for the establishment.")
    @Min(value = 0, message = "The number of car spaces can't negative.")
    private Integer numberOfCarSpaces;
    @NotNull(message = "The number of motorcycle spaces must be informed for the establishment.")
    @Min(value = 0, message = "The number of motorcycle spaces can't negative.")
    private Integer numberOfMotorcycleSpaces;
    @NotNull(message = "The establishment creation date must be provided.")
    private Instant createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "establishment")
    private List<ParkingRecordModel> parkingsRecords;

    public EstablishmentModel(Establishment establishment, Instant createdAt) {
        this.name = establishment.getName();
        this.cnpj = establishment.getCnpj();
        this.address = establishment.getAddress();
        this.phone = establishment.getPhone();
        this.numberOfCarSpaces = establishment.getNumberOfCarSpaces();
        this.numberOfMotorcycleSpaces = establishment.getNumberOfMotorcycleSpaces();
        this.createdAt = createdAt;
    }

    public void update(Establishment establishment) {
        this.name = establishment.getName() != null ? establishment.getName() : this.name;
        this.cnpj = establishment.getCnpj() != null ? establishment.getCnpj() : this.cnpj;
        this.address = establishment.getAddress() != null ? establishment.getAddress() : this.address;
        this.phone = establishment.getPhone() != null ? establishment.getPhone() : this.phone;
        this.numberOfCarSpaces = establishment.getNumberOfCarSpaces() != null ? establishment.getNumberOfCarSpaces() : this.numberOfCarSpaces;
        this.numberOfMotorcycleSpaces = establishment.getNumberOfMotorcycleSpaces() != null ? establishment.getNumberOfMotorcycleSpaces() : this.numberOfMotorcycleSpaces;
        this.parkingsRecords = establishment.getParkingsRecords() != null ? establishment.getParkingsRecords() : this.parkingsRecords;
    }
}
