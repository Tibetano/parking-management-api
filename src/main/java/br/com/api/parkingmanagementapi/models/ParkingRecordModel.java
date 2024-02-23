package br.com.api.parkingmanagementapi.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "parkingRecord")
@Table(name = "parkingRecord")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant input;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant output;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private VehicleModel vehicle;

    @ManyToOne
    @JoinColumn(name = "establishment_id",nullable = false)
    private EstablishmentModel establishment;

}
