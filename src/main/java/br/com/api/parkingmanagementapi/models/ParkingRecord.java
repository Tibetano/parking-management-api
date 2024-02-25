package br.com.api.parkingmanagementapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRecord {
    private Instant input;
    private Instant output;
    private VehicleModel vehicle;
    private EstablishmentModel establishment;
}
