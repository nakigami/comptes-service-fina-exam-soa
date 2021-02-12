package org.sid.comptesservice.dao;

import org.sid.comptesservice.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
