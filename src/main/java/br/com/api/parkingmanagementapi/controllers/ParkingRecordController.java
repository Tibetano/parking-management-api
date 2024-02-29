package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import br.com.api.parkingmanagementapi.dtos.parkingRecord.ParkingRecordRequestDTO;
import br.com.api.parkingmanagementapi.enums.VehicleType;
import br.com.api.parkingmanagementapi.services.ParkingRecordService;
import br.com.api.parkingmanagementapi.services.SummaryService;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/parkingRecord")
public class ParkingRecordController {
    @Autowired
    private ParkingRecordService parkingRecordService;
    @Autowired
    private SummaryService summaryService;
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

    @GetMapping("/input/{establishment}")
    public ResponseEntity<?> totalVehicleEntries(
            @PathVariable(value = "establishment") @CNPJ String cnpjEstablishment,
            @RequestParam VehicleType vehicleType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant finish
    ){
        Long numberOfVehicles = summaryService.totalVehicleEntries(cnpjEstablishment,vehicleType,start,finish);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO(numberOfVehicles.toString() + " " + vehicleType + "(S)."));
    }

    @GetMapping("/output/{establishment}")
    public ResponseEntity<?> totalVehicleDepartures(
            @PathVariable(value = "establishment") @CNPJ String cnpjEstablishment,
            @RequestParam VehicleType vehicleType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant finish
    ){
        Long numberOfVehicles = summaryService.totalVehicleDepartures(cnpjEstablishment,vehicleType,start,finish);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO(numberOfVehicles.toString() + " " + vehicleType + "(S)."));
    }

    @GetMapping("/average-time/{vehicleType}")
    public ResponseEntity<?> averageVehiclesDwellTime(@PathVariable(value = "vehicleType") VehicleType vehicleType){//especificar o estabelecimento
        BigDecimal averageTimeInSeconds = summaryService.averageVehiclesDwellTime(vehicleType);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO(averageTimeInSeconds.intValue() + " seconds."));
    }

    @GetMapping("/occupancy-rate/{establishment}")
    public ResponseEntity<?> parkingOccupancyRate(
            @PathVariable(value = "establishment") @CNPJ String cnpjEstablishment
    ){
        List<Double> occupancyRate = summaryService.parkingOccupancyRate(cnpjEstablishment);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO(occupancyRate.get(0) + "% occupied car spaces. " +
                occupancyRate.get(1) + "% occupied motorcycle spaces."+
                "Total occupancy: "+occupancyRate.get(2)+"% " ));
    }

    @GetMapping("/occupancy-rate")
    public ResponseEntity<?> parkingOccupancyRateList(){
        List<String> parkingOccupancyRateList = new ArrayList<>();
        Map<String,List<Double>> occupancyRateList = summaryService.parkingOccupancyRateList();

        for (var line : occupancyRateList.entrySet()){
            parkingOccupancyRateList.add("CNPJ: " +line.getKey() +", "+ line.getValue().get(0) + "% occupied car spaces. " +
                    line.getValue().get(1) + "% occupied motorcycle spaces."+
                    "Total occupancy: "+line.getValue().get(2)+"% ");
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingOccupancyRateList);
    }

    @GetMapping("/entry-hour/{establishment}")
    public ResponseEntity<?> vehicleEntriesPerHour(
            @PathVariable(value = "establishment") @CNPJ String cnpjEstablishment,
            @RequestParam VehicleType vehicleType,
            @RequestParam Instant date
    ){
        List<String> numberOfVehiclesPerHour = summaryService.vehicleEntriesPerHour(cnpjEstablishment,vehicleType,date);
        return ResponseEntity.status(HttpStatus.OK).body(numberOfVehiclesPerHour);
    }

    @GetMapping("/exit-hour/{establishment}")
    public ResponseEntity<?> vehicleDeparturesPerHour(
            @PathVariable(value = "establishment") @CNPJ String cnpjEstablishment,
            @RequestParam VehicleType vehicleType,
            @RequestParam Instant date
    ){
        List<String> numberOfVehiclesPerHour = summaryService.vehicleDeparturesPerHour(cnpjEstablishment,vehicleType,date);
        return ResponseEntity.status(HttpStatus.OK).body(numberOfVehiclesPerHour);
    }
}
