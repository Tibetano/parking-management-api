package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import br.com.api.parkingmanagementapi.models.EstablishmentModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomParkingRecordsRepository {
    @PersistenceContext
    EntityManager entityManager;

    public Long countParkingRecordsByVehicle(String establishment_id, VehicleType vehicleType){
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records r INNER JOIN vehicles v ON r.vehicle_id = v.id\n" +
                     "WHERE r.establishment_id = '" + establishment_id + "' AND\n" +
                     "v.type = '" + vehicleType + "' AND\n" +
                     "r.output IS NULL";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public Long countVehicleEntry(String establishment_id, VehicleType vehicleType, Instant start, Instant finish){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ").withZone(ZoneOffset.UTC);
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.establishment_id = '"+establishment_id+"' AND\n" +
                     "v.type = '" + vehicleType + "' AND\n" +
                     "pr.input BETWEEN '" + fmt.format(start) + "' AND '" + fmt.format(finish) + "'";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public Long countVehicleExit(String establishment_id,VehicleType vehicleType,Instant start,Instant finish){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ").withZone(ZoneOffset.UTC);
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.establishment_id = '" + establishment_id + "' AND\n" +
                     "    v.type = '" + vehicleType + "' AND\n" +
                     "    pr.input BETWEEN '" + fmt.format(start) + "' AND '" + fmt.format(finish) + "' AND\n" +
                     "    pr.output IS NOT NULL";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public BigDecimal calculateAverageVehicleStayTime(VehicleType vehicleType){
        //Essa consulta encontra o tempo médio da duração dos estacionamentos de carros e retorna esse tempo (((EM SEGUNDOS))).
        String sql = "SELECT AVG(EXTRACT(EPOCH FROM intervals.time))\n" +
                     "FROM(\n" +
                     "SELECT age(pr.output,pr.input) AS time\n" +
                        "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                        "WHERE v.type = '" + vehicleType + "' AND pr.output IS NOT NULL\n" +
                     ") AS intervals\n";
        BigDecimal tempoMedioEmSegundos = (BigDecimal) entityManager.createNativeQuery(sql).getSingleResult();
        return tempoMedioEmSegundos;
    }

    public List<Long> countVehicleReservationInterval(String establishment_id){
        String sql = "SELECT COUNT(v.type)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.output is NULL AND pr.establishment_id = '" + establishment_id + "'\n" +
                     "GROUP BY v.type";
        List<Long> numberParkingRecordOfCarsAndMotorcycles = entityManager.createNativeQuery(sql).getResultList();
        return numberParkingRecordOfCarsAndMotorcycles;
    }

    public Map<String,List<Long>> countOpenVehicleReservationsByEstablishment(){
        Map<String,List<Long>> numberParkingRecordOfCarsAndMotorcyclesByEstablishments = new HashMap<>();
        String sql = "SELECT cars.cnpj, cars.max_car, motorcycles.max_moto, cars.qtde_cars, motorcycles.qtde_motorcycles\n" +
                    "FROM \n" +
                    "(SELECT e.cnpj,e.number_of_car_spaces as max_car, COUNT(*) AS qtde_cars\n" +
                    "FROM parking_records pr \n" +
                    "INNER JOIN vehicles v ON pr.vehicle_id = v.id \n" +
                    "INNER JOIN establishments e ON pr.establishment_id = e.id\n" +
                    "WHERE v.type = 'CAR' AND pr.output IS NULL\n" +
                    "GROUP BY v.type, e.cnpj,e.number_of_car_spaces) AS cars INNER JOIN\n" +
                    "(SELECT e.cnpj,e.number_of_motorcycle_spaces as max_moto, COUNT(*) AS qtde_motorcycles\n" +
                    "FROM parking_records pr \n" +
                    "INNER JOIN vehicles v ON pr.vehicle_id = v.id \n" +
                    "INNER JOIN establishments e ON pr.establishment_id = e.id\n" +
                    "WHERE v.type = 'MOTORCYCLE' AND pr.output IS NULL\n" +
                    "GROUP BY v.type, e.cnpj, e.number_of_motorcycle_spaces) as motorcycles\n" +
                    "ON cars.cnpj = motorcycles.cnpj";
        List<Object[]> DBNumberParkingRecordOfCarsAndMotorcyclesByEstablishments = entityManager.createNativeQuery(sql).getResultList();
        for (Object[] line : DBNumberParkingRecordOfCarsAndMotorcyclesByEstablishments){
            numberParkingRecordOfCarsAndMotorcyclesByEstablishments.put(
                                                                        (String)line[0],
                                                                        List.of(((Integer)line[1]).longValue(),
                                                                        ((Integer)line[2]).longValue(),
                                                                        (Long)line[3],
                                                                        (Long)line[4]));
        }
        return numberParkingRecordOfCarsAndMotorcyclesByEstablishments;
    }

    public Map<BigDecimal,Long> countVehicleEntryPerHour(String establishment_id, VehicleType vehicleType, Instant data){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());
        Map<BigDecimal,Long> hourVehicleList = new HashMap<>();//(hour,amount)
        String sql = "SELECT EXTRACT(HOUR FROM pr.input), COUNT(*)\n" +
                "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                "WHERE pr.establishment_id = '"+establishment_id+"' AND v.type = '"
                +vehicleType+ "' AND DATE(pr.input) = '"+fmt.format(data)+"' \n" +
                "GROUP BY EXTRACT(HOUR FROM pr.input)";
        List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
        for (Object[] line : list){
            hourVehicleList.put((BigDecimal)line[0],(Long) line[1]);
        }
        return hourVehicleList;
    }

    public Map<BigDecimal,Long> CountVehicleDeparturesPerHour(EstablishmentModel establishmentModel,VehicleType vehicleType, Instant date){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());
        Map<BigDecimal,Long> hourVehicleList = new HashMap<>();//(hour,amount)
        String sql = "SELECT EXTRACT(HOUR FROM pr.input), COUNT(*)\n" +
                "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                "WHERE pr.establishment_id = '"+establishmentModel.getId()+"' AND v.type = '"
                +vehicleType+ "' AND DATE(pr.input) = '"+fmt.format(date)+"' AND pr.output IS NOT NULL\n" +
                "GROUP BY EXTRACT(HOUR FROM pr.input)";
        List<Object[]> list = entityManager.createNativeQuery(sql).getResultList();
        for (Object[] line : list){
            hourVehicleList.put((BigDecimal)line[0],(Long) line[1]);
        }
        return hourVehicleList;
    }
}
