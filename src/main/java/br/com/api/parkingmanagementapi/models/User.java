package br.com.api.parkingmanagementapi.models;

import br.com.api.parkingmanagementapi.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private String username;
    private String password;

    private String cpf;
    private String phoneNumber;
    private String email;

    private UserRole role;

    public User(UserModel userModel){
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.cpf = userModel.getCpf();
        this.phoneNumber = userModel.getPhoneNumber();
        this.email = userModel.getEmail();
        this.role = userModel.getRole();
    }

    private User(Builder builder){
        this.username = builder.username;
        this.password = builder.password;
        this.cpf = builder.cpf;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.role = builder.role;
    }

    public static class Builder{
        private String username;
        private String password;
        private String cpf;
        private String phoneNumber;
        private String email;
        private UserRole role;

        public Builder username(String username){
            this.username = username;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder cpf(String cpf){
            this.cpf = cpf;
            return this;
        }
        public Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder userRole(UserRole role){
            this.role = role;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
