package br.com.api.parkingmanagementapi.models;

import br.com.api.parkingmanagementapi.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;

@Entity(name = "users")
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotBlank(message = "The username must be provided.")
    private String username;
    @NotBlank(message = "The password must be provided.")
    private String password;
    @NotNull(message = "The user role must be provided.")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @NotNull(message = "The user creation date must be provided.")
    private Instant createdAt;



    public UserModel(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public UserModel(String username, String password, UserRole role, Instant createdAt){
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public void update(User user){
        this.username = user.getUsername() != null ? user.getUsername() : this.username;
        this.password = user.getPassword() != null ? new BCryptPasswordEncoder().encode(user.getPassword()) : this.password;
        this.role = user.getRole() != null ? user.getRole() : this.role;
    }
}
