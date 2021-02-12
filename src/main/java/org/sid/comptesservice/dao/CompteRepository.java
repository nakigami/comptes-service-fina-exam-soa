package org.sid.comptesservice.dao;

import org.sid.comptesservice.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Long> {
}
