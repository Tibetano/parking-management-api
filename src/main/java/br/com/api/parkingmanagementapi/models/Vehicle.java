package br.com.api.parkingmanagementapi.models;

import br.com.api.parkingmanagementapi.dtos.VehiculeRequestDTO;
import br.com.api.parkingmanagementapi.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private String mark;
    private String model;
    private String color;
    private String plate;
    private VehicleType type;
    private Instant createdAt;
    private List<ParkingRecordModel> parkingsRecords;

    public Vehicle(VehicleModel vehicleModel){
        BeanUtils.copyProperties(vehicleModel,this);
    }

    public Vehicle(VehiculeRequestDTO vehiculeRequestDTO){
        this.mark = vehiculeRequestDTO.mark();
        this.model = vehiculeRequestDTO.model();
        this.color = vehiculeRequestDTO.color();
        this.plate = vehiculeRequestDTO.plate();
        this.type = vehiculeRequestDTO.type();
    }
}
