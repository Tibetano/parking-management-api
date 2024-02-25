package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRecordRepository extends JpaRepository<ParkingRecordModel,Long> {
    public List<ParkingRecordModel> findByEstablishmentAndOutputIsNull(EstablishmentModel establishmentModel);
    public Optional<ParkingRecordModel> findByVehicleAndOutputIsNull(VehicleModel vehicleModel);
}
