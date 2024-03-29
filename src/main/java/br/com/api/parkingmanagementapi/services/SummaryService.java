package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import br.com.api.parkingmanagementapi.repositories.CustomParkingRecordsRepository;
import br.com.api.parkingmanagementapi.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private CustomParkingRecordsRepository customParkingRecordsRepository;

    public Long totalVehicleEntries(String cnpjEstablishment, VehicleType vehicleType, Instant start, Instant finish) {
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstablishment);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Erro: Establishment not found.");
        }
        Long numberOfVehicles = customParkingRecordsRepository.countVehicleEntry(DBEstablishment.get().getId(),vehicleType,start, finish);
        return numberOfVehicles;
    }

    public Long totalVehicleDepartures(String cnpjEstablishment,VehicleType vehicleType,Instant start, Instant finish) {
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstablishment);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Erro: Establishment not found.");
        }
        Long numberOfVehicles = customParkingRecordsRepository.countVehicleExit(DBEstablishment.get().getId(),vehicleType,start, finish);
        return numberOfVehicles;
    }

    public BigDecimal averageVehiclesDwellTime(VehicleType vehicleType) {
        var averageTimeInSeconds = customParkingRecordsRepository.calculateAverageVehicleStayTime(vehicleType);
        BigDecimal zero = new BigDecimal("0.00");
        if(averageTimeInSeconds.compareTo(zero) == 0){//'.compareTo' retorna 0 se os valores forem iguais e 1 caso contrário
            throw new RuntimeException("The average time is zero, as no " + vehicleType.toString().toLowerCase() + "s have parked yet.");
        }
        return averageTimeInSeconds;
    }

    public List<Double> parkingOccupancyRate(String cnpjEstablishment) {
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstablishment);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Erro: Establishment not found.");
        }
        List<Double> numberParkingRecordOfCarsAndMotorcycles = customParkingRecordsRepository.countVehicleReservationInterval(DBEstablishment.get().getId())
                .stream().map(Long::doubleValue).toList();
        if (numberParkingRecordOfCarsAndMotorcycles.isEmpty()){
            throw new RuntimeException("The current occupancy rate is 0%.");
        }
        Double percentageOfCarSpaces =  (Double)(numberParkingRecordOfCarsAndMotorcycles.get(0) / DBEstablishment.get().getNumberOfCarSpaces())*100;
        Double percentageOfMotorcycleSpaces =  (Double)(numberParkingRecordOfCarsAndMotorcycles.get(1) / DBEstablishment.get().getNumberOfMotorcycleSpaces())*100;
        Double totalPercentage = (Double)(percentageOfCarSpaces + percentageOfMotorcycleSpaces)/2;
        List<Double> listOfPercentages = new ArrayList<Double>();
        listOfPercentages.add(percentageOfCarSpaces);
        listOfPercentages.add(percentageOfMotorcycleSpaces);
        listOfPercentages.add(totalPercentage);
        return listOfPercentages;
    }

    public Map<String,List<Double>> parkingOccupancyRateList(){
        Map<String,List<Double>> listOfPercentages = new HashMap<>();
        Map<String,List<Long>> numberParkingRecordOfCarsAndMotorcyclesByEstablishment = customParkingRecordsRepository.countOpenVehicleReservationsByEstablishment();
        if (numberParkingRecordOfCarsAndMotorcyclesByEstablishment.isEmpty()){
            throw new RuntimeException("The current occupancies rate are 0%.");
        }
        for (var line : numberParkingRecordOfCarsAndMotorcyclesByEstablishment.entrySet()){
            Double percentageOfCarSpaces =  (line.getValue().get(2).doubleValue() / line.getValue().get(0).doubleValue())*100;
            Double percentageOfMotorcycleSpaces =  (line.getValue().get(3).doubleValue() / line.getValue().get(1).doubleValue())*100;
            Double totalPercentage = (percentageOfCarSpaces + percentageOfMotorcycleSpaces)/2;
            listOfPercentages.put(
                    line.getKey(),
                    List.of(percentageOfCarSpaces,percentageOfMotorcycleSpaces,totalPercentage)
            );
        }
        return listOfPercentages;
    }

    public List<String> vehicleEntriesPerHour(String cnpjEstablishment, VehicleType vehicleType, Instant date) {
        List<String> numberOfVehiclesPerHour = new ArrayList<String>();
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstablishment);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Erro: Establishment not found.");
        }
        Map<BigDecimal,Long> DBNumberOfVehiclesPerHour = customParkingRecordsRepository.countVehicleEntryPerHour(DBEstablishment.get().getId(), vehicleType, date);
        if(DBNumberOfVehiclesPerHour.isEmpty()){
            throw new RuntimeException("No " + vehicleType.toString().toLowerCase() + "s entered the parking lot at this time.");
        }
        for (var line : DBNumberOfVehiclesPerHour.entrySet()){
            String msg = "Hour: "+line.getKey()+"h"+", amount: "+line.getValue()+" "+vehicleType +"(s).";
            numberOfVehiclesPerHour.add(msg);
        }
        return numberOfVehiclesPerHour;
    }

    public List<String> vehicleDeparturesPerHour(String cnpjEstablishment, VehicleType vehicleType, Instant date) {
        List<String> numberOfVehiclesPerHour = new ArrayList<String>();
        var DBEstablishment = establishmentRepository.findByCnpj(cnpjEstablishment);
        if(DBEstablishment.isEmpty()){
            throw new RuntimeException("Erro: Establishment not found.");
        }
        Map<BigDecimal,Long> DBNumberOfVehiclesPerHour = customParkingRecordsRepository.CountVehicleDeparturesPerHour(DBEstablishment.get(),vehicleType,date);
        if(DBNumberOfVehiclesPerHour.isEmpty()){
            throw new RuntimeException("No " + vehicleType.toString().toLowerCase() + "s left the parking lot at that time.");
        }
        for (var line : DBNumberOfVehiclesPerHour.entrySet()){
            String msg = "Hour: "+line.getKey()+"h"+", amount: "+line.getValue()+" "+vehicleType +"(s).";
            numberOfVehiclesPerHour.add(msg);
        }
        return numberOfVehiclesPerHour;
    }
}
