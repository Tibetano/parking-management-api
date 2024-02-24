package br.com.api.parkingmanagementapi.models;


import br.com.api.parkingmanagementapi.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity(name = "vehicles")
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotBlank(message = "The vehicle's mark must be provided.")
    private String mark;
    @NotBlank(message = "The vehicle's model must be provided.")
    private String model;
    @NotBlank(message = "The vehicle's color must be provided.")
    private String color;
    @NotBlank(message = "The vehicle's license plate must be provided.")
    private String plate;
    @NotNull(message = "The vehicle's type must be provided.")
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    @NotNull(message = "The vehicle creation date must be provided.")
    private Instant createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<ParkingRecordModel> parkingsRecords;

    public VehicleModel(Vehicle vehicle, Instant createdAt) {
        this.mark = vehicle.getMark();
        this.model = vehicle.getModel();
        this.color = vehicle.getColor();
        this.plate = vehicle.getPlate();
        this.type = vehicle.getType();
        this.createdAt = createdAt;
    }

    public void update(Vehicle vehicle){
        this.mark = vehicle.getMark() != null ? vehicle.getMark() : this.mark;
        this.model = vehicle.getModel() != null ? vehicle.getModel() : this.model;
        this.color = vehicle.getColor() != null ? vehicle.getColor() : this.color;
        this.plate = vehicle.getPlate() != null ? vehicle.getPlate() : this.plate;
        this.type = vehicle.getType() != null ? vehicle.getType() : this.type;
        this.parkingsRecords = vehicle.getParkingsRecords() != null ? vehicle.getParkingsRecords() : this.parkingsRecords;
    }
}
