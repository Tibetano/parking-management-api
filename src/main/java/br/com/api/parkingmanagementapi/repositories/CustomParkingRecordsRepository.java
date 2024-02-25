package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
}
