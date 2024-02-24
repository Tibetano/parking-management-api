package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<VehicleModel, UUID> {
    public boolean existsByPlate(String plate);
    public Optional<VehicleModel> findByPlate(String plate);
}
