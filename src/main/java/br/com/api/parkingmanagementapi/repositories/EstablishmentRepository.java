package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.models.EstablishmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablishmentRepository extends JpaRepository<EstablishmentModel,String> {
    public boolean existsByCnpj(String cnpj);
    public Optional<EstablishmentModel> findByCnpj(String cnpj);
}
