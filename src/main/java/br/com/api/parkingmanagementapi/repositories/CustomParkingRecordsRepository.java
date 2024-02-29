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
    private EntityManager entityManager;

    public Long countParkingRecordsByVehicle(String establishment_id, VehicleType vehicleType){
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records r INNER JOIN vehicles v ON r.vehicle_id = v.id\n" +
                     "WHERE r.establishment_id = '" + establishment_id + "' AND\n" +
                     "v.type = '" + vehicleType + "' AND\n" +
                     "r.output IS NULL";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public Long countVehicleEntry(EstablishmentModel establishmentModel, VehicleType vehicleType, Instant start, Instant finish){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ").withZone(ZoneOffset.UTC);
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.establishment_id = '"+establishmentModel.getId()+"' AND\n" +
                     "v.type = '" + vehicleType + "' AND\n" +
                     "pr.input BETWEEN '" + fmt.format(start) + "' AND '" + fmt.format(finish) + "'";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public Long countVehicleExit(EstablishmentModel establishmentModel,VehicleType vehicleType,Instant start,Instant finish){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ").withZone(ZoneOffset.UTC);
        String sql = "SELECT COUNT(*)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.establishment_id = '" + establishmentModel.getId() + "' AND\n" +
                     "    v.type = '" + vehicleType + "' AND\n" +
                     "    pr.input BETWEEN '" + fmt.format(start) + "' AND '" + fmt.format(finish) + "' AND\n" +
                     "    pr.output IS NOT NULL";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    public BigDecimal CalculateAverageVehicleStayTime(VehicleType vehicleType){
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

    public List<Long> countVehicleReservationInterval(EstablishmentModel establishmentModel){
        String sql = "SELECT COUNT(v.type)\n" +
                     "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                     "WHERE pr.output is NULL AND pr.establishment_id = '" + establishmentModel.getId() + "'\n" +
                     "GROUP BY v.type";
        List<Long> numberParkingRecordOfCarsAndMotorcycles = entityManager.createNativeQuery(sql).getResultList();
        return numberParkingRecordOfCarsAndMotorcycles;
    }

    //public List<Pair<BigDecimal,Long>> countVehicleEntryPerHour(EstablishmentModel establishmentModel, VehicleType vehicleType, Instant data){
    public Map<BigDecimal,Long> countVehicleEntryPerHour(EstablishmentModel establishmentModel, VehicleType vehicleType, Instant data){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());
        Map<BigDecimal,Long> hourVehicleList = new HashMap<>();//(hour,amount)
        String sql = "SELECT EXTRACT(HOUR FROM pr.input), COUNT(*)\n" +
                "FROM parking_records pr INNER JOIN vehicles v ON pr.vehicle_id = v.id\n" +
                "WHERE pr.establishment_id = '"+establishmentModel.getId()+"' AND v.type = '"
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
