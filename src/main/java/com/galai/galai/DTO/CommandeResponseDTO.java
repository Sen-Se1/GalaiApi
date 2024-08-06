package com.galai.galai.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandeResponseDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    private String pays;
    private String ville;
    private Integer codePostal;
    private Integer numeroTelephone;
    private LocalDateTime dateCreation;
    private List<LigneCmdResponseDTO> lignesCommande;
    private Double prixTotal;
}
