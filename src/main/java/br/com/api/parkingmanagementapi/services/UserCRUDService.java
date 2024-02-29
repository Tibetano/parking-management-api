package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.enums.UserRole;
import br.com.api.parkingmanagementapi.models.User;
import br.com.api.parkingmanagementapi.models.UserModel;
import br.com.api.parkingmanagementapi.repositories.UserRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserCRUDService {
    @Autowired
    private UserRepositiry userRepositiry;

    public User findUser(String username){
        var DBUser = userRepositiry.findByUsername(username);
        if (DBUser.isEmpty()){
            throw new RuntimeException("Erro: users not found.");
        }
        return new User(DBUser.get());
    }

    public List<User> findAllUsers(){
        var DBUsers = userRepositiry.findAll().stream().map(User::new).toList();
        if (DBUsers.isEmpty()){
            throw new RuntimeException("Erro: no user found.");
        }
        return DBUsers;
    }

    public void registerUser(User user){
        var existsUser = userRepositiry.existsByUsername(user.getUsername());
        if(existsUser){
            throw new RuntimeException("Username already taken.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        UserModel newUser = new UserModel(user.getUsername(), encryptedPassword,user.getCpf(),user.getPhoneNumber(),user.getEmail(), UserRole.USER, Instant.now());
        userRepositiry.save(newUser);
    }

    public void updateUser(String username, User user){
        var DBUser = userRepositiry.findByUsername(username);
        if (DBUser.isEmpty()){
            throw new RuntimeException("Erro: user not found.");
        }
        DBUser.get().update(user);
        System.out.println(DBUser.get());
        System.out.println("++++++++++++++++++++++");
        System.out.println(DBUser.get());
        userRepositiry.save(DBUser.get());
    }

    public void deleteUser(String username){
        var DBUser = userRepositiry.findByUsername(username);
        if (DBUser.isEmpty()){
            throw new RuntimeException("Erro: user not found.");
        }
        userRepositiry.delete(DBUser.get());
    }
}
