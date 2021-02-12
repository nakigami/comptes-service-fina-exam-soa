package org.sid.comptesservice.services;


import org.sid.comptesservice.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLIENT-SERVICE")
public interface ClientServiceClient {
    @GetMapping(path = "/clients/{id}")
    public Client findClientById(@PathVariable("id") Long id);
}
