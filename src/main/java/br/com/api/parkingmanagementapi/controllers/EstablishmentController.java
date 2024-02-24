package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import br.com.api.parkingmanagementapi.dtos.establishment.EstablishmentRequestDTO;
import br.com.api.parkingmanagementapi.dtos.establishment.EstablishmentResponseDTO;
import br.com.api.parkingmanagementapi.models.Establishment;
import br.com.api.parkingmanagementapi.services.EstablishmentCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/establishment")
public class EstablishmentController {
    @Autowired
    private EstablishmentCRUDService establishmentCRUDService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> findEstablishment(@PathVariable(value = "cnpj") String cnpj){
        String cnpjTreated = cnpj.replace("_","/");
        Establishment establishment = establishmentCRUDService.findEstablishment(cnpjTreated);
        return ResponseEntity.status(HttpStatus.OK).body(new EstablishmentResponseDTO(establishment));
    }

    @GetMapping
    public ResponseEntity<?> findAllEstablishments(){
        List<EstablishmentResponseDTO> establishmentList = establishmentCRUDService.findAllEstablishments().stream().map(EstablishmentResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(establishmentList);
    }

    @PostMapping
    public ResponseEntity<?> registerEstablishment(@RequestBody @Valid EstablishmentRequestDTO establishmentRequestDTO){
        establishmentCRUDService.registerEstablishment(new Establishment(establishmentRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDTO("Establishment registred successfully."));
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<?> updateEstablishment(@PathVariable(value = "cnpj") String cnpj, @RequestBody EstablishmentRequestDTO establishmentRequestDTO){
        String cnpjTratado = cnpj.replace("_","/");
        establishmentCRUDService.updateEstablishment(cnpjTratado,new Establishment(establishmentRequestDTO));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("Establishment updated successfully."));
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<?> deleteEstablishment(@PathVariable(value = "cnpj") String cnpj){
        String cnpjTreated = cnpj.replace("_","/");
        establishmentCRUDService.deleteEstablishment(cnpjTreated);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponseDTO("Establishment deleted successfully."));
    }
}
