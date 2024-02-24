package br.com.api.parkingmanagementapi.services;

import br.com.api.parkingmanagementapi.models.Establishment;
import br.com.api.parkingmanagementapi.models.EstablishmentModel;
import br.com.api.parkingmanagementapi.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EstablishmentCRUDService {
    @Autowired
    private EstablishmentRepository establishmentRepository;

    public Establishment findEstablishment(String cnpj){
        var DBEstablishment = establishmentRepository.findByCnpj(cnpj);
        if (DBEstablishment.isEmpty()){
            throw new RuntimeException("Error: establishment not found.");
        }
        return new Establishment(DBEstablishment.get());
    }

    public List<Establishment> findAllEstablishments(){
        var DBEstablishmentsList = establishmentRepository.findAll().stream().map(Establishment::new).toList();
        for(Establishment e : DBEstablishmentsList){
            System.out.println(e);
        }
        if (DBEstablishmentsList.isEmpty()){
            throw new RuntimeException("Error: no establishment found.");
        }
        return DBEstablishmentsList;
    }

    public void registerEstablishment(Establishment establishment){
        var existsEstablishment = establishmentRepository.existsByCnpj(establishment.getCnpj());
        if (existsEstablishment){
            throw new RuntimeException("Error: CNPJ already registered.");
        }
        var newEstablishment = new EstablishmentModel(establishment, Instant.now());
        establishmentRepository.save(newEstablishment);
    }

    public void updateEstablishment(String cnpj,Establishment establishment){
        var DBEstablishment = establishmentRepository.findByCnpj(cnpj);
        if (DBEstablishment.isEmpty()){
            throw new RuntimeException("Error: establishment not found.");
        }
        DBEstablishment.get().update(establishment);
        establishmentRepository.save(DBEstablishment.get());
    }

    public void deleteEstablishment(String cnpj){
        var DBEstabelecimento = establishmentRepository.findByCnpj(cnpj);
        if (DBEstabelecimento.isEmpty()){
            throw new RuntimeException("Error: establishment not found.");
        }
        establishmentRepository.delete(DBEstabelecimento.get());
    }
}
