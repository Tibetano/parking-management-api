package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.models.Vehicle;
import br.com.api.parkingmanagementapi.models.VehicleModel;
import br.com.api.parkingmanagementapi.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class VehicleCRUDService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle findVehicle(String plate){
        var DBVehicle = vehicleRepository.findByPlate(plate);
        if (DBVehicle.isEmpty()){
            throw new RuntimeException("Error: vehicle not found.");
        }
        return new Vehicle(DBVehicle.get());
    }

    public List<Vehicle> findAllVehicles(){
        var DBVehiclesList = vehicleRepository.findAll().stream().map(Vehicle::new).toList();
        if (DBVehiclesList.isEmpty()){
            throw new RuntimeException("Error: no vehicles found.");
        }
        return DBVehiclesList;
    }

    public void registerVehicle(Vehicle vehicle){
        var existsVehicle = vehicleRepository.existsByPlate(vehicle.getPlate());
        if (existsVehicle){
            throw new RuntimeException("Error: plate already registered.");
        }
        var newVehicle = new VehicleModel(vehicle, Instant.now());
        var res = vehicleRepository.save(newVehicle);
    }

    public void updateVehicle(String plate,Vehicle vehicle){
        var DBVehicle = vehicleRepository.findByPlate(plate);
        if (DBVehicle.isEmpty()){
            throw new RuntimeException("Error: vehicle not found.");
        }
        DBVehicle.get().update(vehicle);
        vehicleRepository.save(DBVehicle.get());
    }

    public void deleteVehicle(String plate){
        var DBVehicle = vehicleRepository.findByPlate(plate);
        if (DBVehicle.isEmpty()){
            throw new RuntimeException("Error: vehicle not found.");
        }
        vehicleRepository.delete(DBVehicle.get());
    }
}
