package org.sid.comptesservice;

import org.sid.comptesservice.entities.Compte;
import org.sid.comptesservice.entities.Operation;
import org.sid.comptesservice.dao.CompteRepository;
import org.sid.comptesservice.dao.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class ComptesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComptesServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(CompteRepository compteRepository, OperationRepository operationRepository) {
        return args -> {
            Compte compte = compteRepository.save(new Compte(null, "cp-251263255", 19552, new Date(), "COURANT", "ACTIVE", null, 1l,null));
            List<Operation> operations = new ArrayList<>();
            operations.add(operationRepository.save(new Operation(null, 4152.00, "versement", compte)));
            operations.add(operationRepository.save(new Operation(null, 2500.00, "versement", compte)));
            compte.setOperations(operations);
            operations.forEach(op -> {
                operationRepository.save(op);
            });
        };
    }
}