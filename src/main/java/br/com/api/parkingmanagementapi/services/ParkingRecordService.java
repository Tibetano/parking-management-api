package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import br.com.api.parkingmanagementapi.models.ParkingRecord;
import br.com.api.parkingmanagementapi.models.ParkingRecordModel;
import br.com.api.parkingmanagementapi.repositories.CustomParkingRecordsRepository;
import br.com.api.parkingmanagementapi.repositories.EstablishmentRepository;
import br.com.api.parkingmanagementapi.repositories.ParkingRecordRepository;
import br.com.api.parkingmanagementapi.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ParkingRecordService {
    @Autowired
    private ParkingRecordRepository parkingRecordRepository;
    @Autowired
    private CustomParkingRecordsRepository customParkingRecordsRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public void startParkingRecord(String cnpjEstabelecimento,String plate){
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstabelecimento);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Error: establishment doesn't registered.");
        }
        var DBVehicle = vehicleRepository.findByPlate(plate);
        if(DBVehicle.isEmpty()){
            throw new RuntimeException("Error: vehicle doesn't registered.");
        }
        var DBParkingRecord = parkingRecordRepository.findByVehicleAndOutputIsNull(DBVehicle.get());
        if (DBParkingRecord.isPresent()){
            throw new RuntimeException("Error: vehicle already parked.");
        }
        if (DBVehicle.get().getType()== VehicleType.MOTORCYCLE) {
            var numberOfMotorcyclesParkingRecords = customParkingRecordsRepository.countParkingRecordsByVehicle(DBEstablishment.get().getId(),DBVehicle.get().getType());
            if(numberOfMotorcyclesParkingRecords.intValue() == DBEstablishment.get().getNumberOfMotorcycleSpaces()) {
                throw new RuntimeException("Error: the number of motorcycle spaces is full.");
            }
        } else {
            var numberOfCarsParkingRecords = customParkingRecordsRepository.countParkingRecordsByVehicle(DBEstablishment.get().getId(),DBVehicle.get().getType());
            if(numberOfCarsParkingRecords.intValue() == DBEstablishment.get().getNumberOfCarSpaces()){
                throw new RuntimeException("Error: the number of car spaces is full.");
            }
        }
        ParkingRecord newParkingRecord = new ParkingRecord();
        newParkingRecord.setInput(Instant.now());
        newParkingRecord.setEstablishment(DBEstablishment.get());
        newParkingRecord.setVehicle(DBVehicle.get());
        parkingRecordRepository.save(new ParkingRecordModel(newParkingRecord));
    }

    public void finishParkingRecord(String plate){
        var DBVehicle = vehicleRepository.findByPlate(plate);
        if(DBVehicle.isEmpty()){
            throw new RuntimeException("Error: vehicle doesn't registered.");
        }
        var DBParkingRecord = parkingRecordRepository.findByVehicleAndOutputIsNull(DBVehicle.get());
        if (DBParkingRecord.isEmpty()){
            throw new RuntimeException("Error: vehicle not found.");
        }
        DBParkingRecord.get().setOutput(Instant.now());
        parkingRecordRepository.save(DBParkingRecord.get());
    }
}
