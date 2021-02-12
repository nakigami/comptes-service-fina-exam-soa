package org.sid.comptesservice.entities;


import lombok.Data;

@Data
public class Client {
    private Long id;
    private String code;
    private String nom;
    private String email;
}
