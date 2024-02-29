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

    @NotBlank(message = "The cpf must be provided.")
    private String cpf;
    @NotBlank(message = "The phone number must be provided.")
    private String phoneNumber;
    @NotBlank(message = "The email must be provided.")
    private String email;

    @NotNull(message = "The user role must be provided.")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @NotNull(message = "The user creation date must be provided.")
    private Instant createdAt;



    public UserModel(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.cpf = user.getCpf();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public UserModel(String username, String password, String cpf, String phoneNumber, String email, UserRole role, Instant createdAt){
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public void update(User user){
        System.out.println("cpf: "+this.cpf);
        this.username = user.getUsername() != null ? user.getUsername() : this.username;
        this.password = user.getPassword() != null ? new BCryptPasswordEncoder().encode(user.getPassword()) : this.password;
        this.cpf = user.getCpf() != null ? user.getCpf() : this.cpf;
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : this.phoneNumber;
        this.email = user.getEmail() != null ? user.getEmail() : this.email;
        this.role = user.getRole() != null ? user.getRole() : this.role;
        System.out.println("cpf: "+this.cpf);
    }
}
