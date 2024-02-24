package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import br.com.api.parkingmanagementapi.dtos.vehicle.VehicleRequestDTO;
import br.com.api.parkingmanagementapi.dtos.vehicle.VehicleResponseDTO;
import br.com.api.parkingmanagementapi.models.Vehicle;
import br.com.api.parkingmanagementapi.services.VehicleCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleCRUDService vehicleCRUDService;

    @GetMapping("/{plate}")
    public ResponseEntity<?> findVehicle(@PathVariable(value = "plate") String plate){
        Vehicle vehicle = vehicleCRUDService.findVehicle(plate);
        return ResponseEntity.status(HttpStatus.OK).body(new VehicleResponseDTO(vehicle));
    }

    @GetMapping
    public ResponseEntity<?> findAllVehicles(){
        List<VehicleResponseDTO> vehicleList = vehicleCRUDService.findAllVehicles().stream().map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(vehicleList);
    }

    @PostMapping
    public ResponseEntity<?> registerVehicle(@Valid @RequestBody VehicleRequestDTO vehiculeRequestDTO){
        vehicleCRUDService.registerVehicle(new Vehicle(vehiculeRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDTO("Vehicule registred successfully."));
    }

    @PutMapping("/{plate}")
    public ResponseEntity<?> updateVehicle(@PathVariable(value = "plate") String plate, @RequestBody VehicleRequestDTO vehiculeRequestDTO){
        vehicleCRUDService.updateVehicle(plate,new Vehicle(vehiculeRequestDTO));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("Vehicule updated successfully."));
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<?> deleteVehicle(@PathVariable(value = "plate") String plate){
        vehicleCRUDService.deleteVehicle(plate);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("Vehicule deleted successfully."));
    }
}
