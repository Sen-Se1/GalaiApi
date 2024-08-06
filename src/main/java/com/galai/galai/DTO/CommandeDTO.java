package com.galai.galai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeDTO {
    private String nom;
    private String prenom;
    private String adresse;
    private String pays;
    private String ville;
    private Integer codePostal;
    private Integer numeroTelephone;
    private List<LigneCmdDTO> lignesCommande;
}
