package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.models.User;
import br.com.api.parkingmanagementapi.repositories.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private UserRepositiry userRepositiry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userModel = userRepositiry.findByUsername(username);
        if(userModel.isEmpty()){
            throw new RuntimeException("User not found.");
        }
        UserDetails userDetails = new User(userModel.get());
        return userDetails;
    }
}
