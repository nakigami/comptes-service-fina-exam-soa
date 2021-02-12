package org.sid.comptesservice.services;

import org.sid.comptesservice.entities.Compte;
import org.sid.comptesservice.entities.Operation;
import org.sid.comptesservice.dao.CompteRepository;
import org.sid.comptesservice.dao.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ICompteServiceImp implements ICompetService{

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    ClientServiceClient clientServiceClient;

    @Autowired
    OperationRepository operationRepository;

    @Override
    public Compte ajouterCompte(Compte c) {
        return compteRepository.save(c);
    }

    @Override
    public Operation versement(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("virement");
        c.setSolde(c.getSolde()+montant);
        compteRepository.save(c);

        return operationRepository.save(o);
    }

    @Override
    public Operation retirer(Long id, double montant) {
        Compte c=compteRepository.getOne(id);
        Operation o=new Operation();
        o.setCompte(c);
        o.setMontant(montant);
        o.setType("retrait");
        c.setSolde(c.getSolde()-montant);
        compteRepository.save(c);

        return operationRepository.save(o);
    }

    @Override
    @Transactional
    public void virement(Long id1, Long id2 ,double montant) {
        retirer(id1,montant);
        versement(id2,montant);
    }

    @Override
    public Compte getCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setClient(clientServiceClient.findClientById(c.getClient_ID()));

        return c;
    }

    @Override
    public Compte activerCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("active");
        compteRepository.save(c);

        return c;
    }

    @Override
    public Compte suspendreCompte(Long id) {
        Compte c=compteRepository.getOne(id);
        c.setEtat("suspendu");
        compteRepository.save(c);

        return c;
    }

    @Override
    public List<Operation> getOperation(Long id) {
        Compte c=compteRepository.getOne(id);

        return c.getOperations();
    }
}
