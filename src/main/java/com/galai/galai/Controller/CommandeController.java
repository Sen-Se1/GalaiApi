package com.galai.galai.Controller;

import com.galai.galai.DTO.*;
import com.galai.galai.Entity.Commande;
import com.galai.galai.Entity.LigneCmd;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.CommandeService;
import com.galai.galai.Service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("commande")
public class CommandeController {
    private final CommandeService CS;
    private final ProduitService PS;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CommandeDTO commandeDTO) {
        try {
            Commande commande = new Commande();
            commande.setNom(commandeDTO.getNom());
            commande.setPrenom(commandeDTO.getPrenom());
            commande.setAdresse(commandeDTO.getAdresse());
            commande.setPays(commandeDTO.getPays());
            commande.setVille(commandeDTO.getVille());
            commande.setCodePostal(commandeDTO.getCodePostal());
            commande.setNumeroTelephone(commandeDTO.getNumeroTelephone());

            for (LigneCmdDTO ligneCmdDTO : commandeDTO.getLignesCommande()) {
                Produit produit = PS.getProduitById(ligneCmdDTO.getProduitId());

                if (produit.getQtt() < ligneCmdDTO.getQtt()) {
                    throw new IllegalArgumentException("Insufficient quantity for product with ID " + ligneCmdDTO.getProduitId());
                }

                Prix prix = produit.getPrixList().stream()
                        .filter(p -> p.getId().equals(ligneCmdDTO.getPrixId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Price with ID " + ligneCmdDTO.getPrixId() + " does not exist for product with ID " + ligneCmdDTO.getProduitId()));

                Integer remise = produit.getRemise();
                Double prixApresRemise = prix.getPrix() - (prix.getPrix() * remise / 100.0);

                LigneCmd ligneCmd = new LigneCmd();
                ligneCmd.setProduit(produit);
                ligneCmd.setQtt(ligneCmdDTO.getQtt());
                ligneCmd.setGrammage(prix.getGrammage());
                ligneCmd.setPrix(prixApresRemise);
                ligneCmd.setCommande(commande);

                commande.getLignesCommande().add(ligneCmd);
            }

            Commande savedCommande = CS.save(commande);

            CommandeResponseDTO responseDTO = new CommandeResponseDTO(
                    savedCommande.getId(),
                    savedCommande.getNom(),
                    savedCommande.getPrenom(),
                    savedCommande.getAdresse(),
                    savedCommande.getPays(),
                    savedCommande.getVille(),
                    savedCommande.getCodePostal(),
                    savedCommande.getNumeroTelephone(),
                    savedCommande.getDateCreation(),
                    savedCommande.getLignesCommande().stream()
                            .map(ligneCmd -> new LigneCmdResponseDTO(
                                    new ProduitResponseDTO(
                                            ligneCmd.getProduit().getId(),
                                            ligneCmd.getProduit().getNom()
                                    ),
                                    ligneCmd.getPrix(),
                                    ligneCmd.getGrammage(),
                                    ligneCmd.getQtt()
                            ))
                            .collect(Collectors.toList()),
                    savedCommande.getPrixTotal()
            );

            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCommande() {
        try {
            List<Commande> commandeList = CS.getAllCommande();
            List<CommandeResponseDTO> responseDTOList = commandeList.stream().map(commande -> new CommandeResponseDTO(
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
                            .map(ligneCmd -> new LigneCmdResponseDTO(
                                    new ProduitResponseDTO(
                                            ligneCmd.getProduit().getId(),
                                            ligneCmd.getProduit().getNom()
                                    ),
                                    ligneCmd.getPrix(),
                                    ligneCmd.getGrammage(),
                                    ligneCmd.getQtt()
                            ))
                            .collect(Collectors.toList()),
                    commande.getPrixTotal()
            )).collect(Collectors.toList());

            return ResponseEntity.ok().body(responseDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            CS.delete(id);
            return ResponseEntity.ok().body("Commande : " + id + " supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
