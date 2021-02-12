package org.sid.comptesservice.services;

import org.sid.comptesservice.entities.Compte;
import org.sid.comptesservice.entities.Operation;

import java.util.List;

public interface ICompetService {

    public Compte ajouterCompte(Compte c);
    public Operation versement(Long id,double montant);
    public Operation retirer(Long id,double montant);
    public void virement(Long id1,Long id2,double montan);
    public Compte getCompte(Long id);
    public Compte activerCompte(Long id);
    public Compte suspendreCompte(Long id);
    public List<Operation> getOperation(Long id);
}
