package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.enums.VehicleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class CustomParkingRecordsRepositoryTest {

    private CustomParkingRecordsRepository repository;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        entityManager = mock(EntityManager.class);
        repository = new CustomParkingRecordsRepository();
        repository.entityManager = entityManager;
    }

    @Test
    @DisplayName("Deve retornar um valor numérico.")
    void countParkingRecordsByVehicle() {

        //Mock expected result from query
        Long expectedResult = 10L;

        // Mocking the EntityManager and Query
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);

        // Invoke the method under test
        Long result = repository.countParkingRecordsByVehicle("establishmentId", VehicleType.CAR);

        // Verify that the correct query was executed
        verify(entityManager).createNativeQuery(anyString());

        // Verify that the result matches the expected value
        assertEquals(expectedResult, result);

    }

    @Test
    void countVehicleEntry() {
        Long expectedResult = 5L;
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        Long result = repository.countVehicleEntry("establishment_id", VehicleType.MOTORCYCLE, Instant.now(),Instant.now());
        verify(entityManager).createNativeQuery(anyString());
        assertEquals(expectedResult,result);
    }

    @Test
    void countVehicleExit() {
        Long expectedResult = 2L;
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        Long result = repository.countVehicleExit("establishment_id", VehicleType.MOTORCYCLE, Instant.now(),Instant.now());
        verify(entityManager).createNativeQuery(anyString());
        assertEquals(expectedResult,result);
    }

    @Test
    void calculateAverageVehicleStayTime() {
        BigDecimal expectedResult = new BigDecimal(2.123);
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedResult);
        BigDecimal result = repository.calculateAverageVehicleStayTime(VehicleType.MOTORCYCLE);
        verify(entityManager).createNativeQuery(anyString());
        assertEquals(expectedResult,result);
    }

    @Test
    void countVehicleReservationInterval() {
        List<Long> expectedResult = List.of(10L,5L);
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        List<Long> result = repository.countVehicleReservationInterval("establishment_id");
        verify(entityManager).createNativeQuery(anyString());
        assertEquals(expectedResult,result);
    }

    @Test
    void countOpenVehicleReservationsByEstablishment() {
        List<Object[]> expectedResult = new ArrayList<>();
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        Map<String,List<Long>> result = repository.countOpenVehicleReservationsByEstablishment();
        verify(entityManager).createNativeQuery(anyString());
    }

    @Test
    void countVehicleEntryPerHour() {
        List<Object[]> expectedResult = new ArrayList<>();

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        Map<BigDecimal,Long> result = repository.countVehicleEntryPerHour("65489654",VehicleType.CAR,Instant.now());
        verify(entityManager).createNativeQuery(anyString());
    }

    @Test
    void countVehicleDeparturesPerHour() {
        List<Object[]> expectedResult = new ArrayList<>();
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        Map<String,List<Long>> result = repository.countOpenVehicleReservationsByEstablishment();
        verify(entityManager).createNativeQuery(anyString());
    }
}