package com.galai.galai.DTO;

import com.galai.galai.Entity.Commande;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommandeResponseDTO {
        private Integer id;
        private String nom;
        private String prenom;
        private String adresse;
        private String pays;
        private String ville;
        private Integer codePostal;
        private Integer numeroTelephone;
        private LocalDateTime dateCreation;
        private List<LigneCmdDTO.LigneCmdResponseDTO> lignesCommande;
        private Double prixTotal;

        public static CommandeResponseDTO convertToDto(Commande commande) {
            return new CommandeResponseDTO(
                    commande.getId(),
                    commande.getNom(),
                    commande.getPrenom(),
                    commande.getAdresse(),
                    commande.getPays(),
                    commande.getVille(),
                    commande.getCodePostal(),
                    commande.getNumeroTelephone(),
                    commande.getDateCreation(),
                    commande.getLignesCommande().stream()
                            .map(LigneCmdDTO.LigneCmdResponseDTO::convertToDto)
                            .collect(Collectors.toList()),
                    commande.getPrixTotal()
            );
        }
    }
}
