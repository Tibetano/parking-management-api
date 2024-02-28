package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import br.com.api.parkingmanagementapi.dtos.user.UserRequestDTO;
import br.com.api.parkingmanagementapi.enums.UserRole;
import br.com.api.parkingmanagementapi.models.User;
import br.com.api.parkingmanagementapi.services.UserCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserCRUDService userCRUDService;

    @GetMapping("/{username}")
    public ResponseEntity<?> findUser(@PathVariable(value = "username") String username){
        User user = userCRUDService.findUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers(){
        List<User> usersList = userCRUDService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        userCRUDService.registerUser(new User(userRequestDTO.username(),userRequestDTO.password(), null));
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDTO("User registered successfully."));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "username") String username, @Valid @RequestBody UserRequestDTO userRequestDTO){
        userCRUDService.updateUser(new User(username,userRequestDTO.password(),null));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("User updated successfully."));
    }

    @PutMapping("/e/{username}")
    public ResponseEntity<?> updateUserRole(@PathVariable(value = "username") String username, UserRole role){
        userCRUDService.updateUser(new User(username,null,role));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("User updated successfully."));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username){
        userCRUDService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("User deleted successfully."));
    }
}
