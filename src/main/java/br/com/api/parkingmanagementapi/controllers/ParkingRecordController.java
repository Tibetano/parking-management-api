package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import br.com.api.parkingmanagementapi.dtos.parkingRecord.ParkingRecordRequestDTO;
import br.com.api.parkingmanagementapi.services.ParkingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/parkingRecord")
public class ParkingRecordController {
    @Autowired
    private ParkingRecordService parkingRecordService;
    @PostMapping
    public ResponseEntity<?> startParkingRecord(@RequestBody ParkingRecordRequestDTO parkingRecordRequestDTO){
        parkingRecordService.startParkingRecord(parkingRecordRequestDTO.cnpj(),parkingRecordRequestDTO.plate());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDTO("Vehicle parked successfully."));
    }

    @PutMapping("/{plate}")
    public ResponseEntity<?> finishParkingRecord(@PathVariable(value = "plate") String plate){
        parkingRecordService.finishParkingRecord(plate);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponseDTO("Vehicle released successfully."));
    }
}
