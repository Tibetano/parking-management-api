package br.com.api.parkingmanagementapi.repositories;

import br.com.api.parkingmanagementapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositiry extends JpaRepository<UserModel, String> {
    //UserDetails findByUsuario(String usuario);
    Optional<UserModel> findByUsername(String username);
    boolean existsByUsername(String username);
}
