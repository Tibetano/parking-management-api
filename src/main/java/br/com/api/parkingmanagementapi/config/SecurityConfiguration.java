package br.com.api.parkingmanagementapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(HttpMethod.POST,
                                "/v1/user",
                                "/v1/auth/login"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/v1/parkingRecord/occupancy-rate/{establishment}",
                                "/v1/parkingRecord/occupancy-rate"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/v1/user/{username}").permitAll()
                        //.requestMatchers(HttpMethod.DELETE, "").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/v1/parkingRecord/input/{establishment}",
                                "/v1/parkingRecord/output/{establishment}",
                                "/v1/parkingRecord/average-time/{vehicleType}",
                                //"/v1/parkingRecord/occupancy-rate/{establishment}",
                                "/v1/parkingRecord/entry-hour/{establishment}",
                                "/v1/parkingRecord/exit-hour/{establishment}",
                                "/v1/establishment/{cnpj}",
                                "/v1/establishment",
                                "/v1/user/{username}",
                                "/v1/user",
                                "/v1/vehicle",
                                "/v1/vehicle/{plate}"
                                ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/v1/parkingRecord",
                                "/v1/establishment",
                                "/v1/vehicle"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/v1/establishment/{cnpj}",
                                "/v1/vehicle/{plate}",
                                "/v1/e/user/{username}",
                                "/v1/parkingRecord/{plate}"
                        ).hasRole("ADMIN")//colocar uma verificação para que um usuário mau intencionado não consiga apagar veiculos de outros usuário e depois remover esse endpoint de apagar veiculos da restrição de só pra admins.
                        .requestMatchers(HttpMethod.DELETE,
                                "/v1/establishment/{cnpj}",
                                "/v1/vehicle/{plate}",
                                "/v1/user/{username}"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
