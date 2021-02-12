package org.sid.comptesservice.web;


import org.sid.comptesservice.entities.Compte;
import org.sid.comptesservice.entities.Operation;
import org.sid.comptesservice.dao.CompteRepository;
import org.sid.comptesservice.services.ClientServiceClient;
import org.sid.comptesservice.services.ICompetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CompteController {

    @Autowired
    KafkaTemplate<String, Operation> template;

    private String topic = "comptestopic";

    @Autowired
    ClientServiceClient clientServiceClient;

    @Autowired
    ICompetService iCompetService;

    @Autowired
    CompteRepository compteRepository;

    @GetMapping("Comptes")
    public List<Compte> getCompte() {
        List<Compte> compts = compteRepository.findAll();
        compts.forEach(compte -> {
            compte.setClient(clientServiceClient.findClientById(compte.getClient_ID()));
        });
        return compts;
    }

    @PostMapping("Comptes")
    public Compte ajouterCompte(@RequestBody Compte compte) {
        return iCompetService.ajouterCompte(compte);
    }

    @PostMapping("comptes/versement")
    public Operation versement(@RequestParam("id") Long id, @RequestParam("montant") double montant) {
        // Send kafka message
        Operation operation = iCompetService.versement(id, montant);
        template.send(topic, "key-"+operation.getId(), operation);

        return operation;
    }

    @PostMapping("compte/activer")
    public Compte activerCompte(@RequestParam("id") Long id) {
        return iCompetService.activerCompte(id);
    }

    @PostMapping("compte/suspendre")
    public Compte suspendreCompte(@RequestParam("id") Long id) {
        return iCompetService.suspendreCompte(id);
    }

    @GetMapping("compte/{id}")
    public Compte getCompte(@PathVariable("id") Long id) {
        return iCompetService.getCompte(id);
    }

    @GetMapping("compte/operation/{id}")
    public List<Operation> getOperation(@PathVariable("id") Long id) {
        return iCompetService.getOperation(id);
    }

    @PostMapping("compte/retrait")
    public Operation retrait(@RequestParam("id") Long id, @RequestParam("montant") double montant) {
        // Send kafka message
        Operation operation = iCompetService.retirer(id, montant);
        template.send(topic, "key-"+operation.getId(), operation);
        return operation;
    }
}
