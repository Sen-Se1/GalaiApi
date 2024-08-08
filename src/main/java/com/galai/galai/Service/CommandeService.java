package com.galai.galai.Service;

import com.galai.galai.DTO.CommandeDTO;
import com.galai.galai.DTO.LigneCmdDTO;
import com.galai.galai.Entity.Commande;
import com.galai.galai.Entity.LigneCmd;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Repository.CommandeRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeRepository CR;
    private final ProduitService PS;
    private final EmailService ES;

    public Commande save(CommandeDTO commandeDTO) throws MessagingException {
        Commande commande = new Commande();
        commande.setNom(commandeDTO.getNom());
        commande.setPrenom(commandeDTO.getPrenom());
        commande.setAdresse(commandeDTO.getAdresse());
        commande.setPays(commandeDTO.getPays());
        commande.setVille(commandeDTO.getVille());
        commande.setCodePostal(commandeDTO.getCodePostal());
        commande.setNumeroTelephone(commandeDTO.getNumeroTelephone());

        for (LigneCmdDTO ligneCmdDTO : commandeDTO.getLignesCommande()) {
            Produit produit = PS.getExistingProduitById(ligneCmdDTO.getProduitId());

            Prix prix = produit.getPrixList().stream()
                    .filter(p -> p.getId().equals(ligneCmdDTO.getPrixId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Price with ID " + ligneCmdDTO.getPrixId() + " does not exist for product with ID " + ligneCmdDTO.getProduitId()));

            if (ligneCmdDTO.getQtt() > prix.getQtt()) {
                throw new IllegalArgumentException("Insufficient quantity for product with ID " + ligneCmdDTO.getProduitId());
            }

            Integer remise = prix.getRemise();
            Double prixApresRemise = prix.getPrix() - (prix.getPrix() * remise / 100.0);

            LigneCmd ligneCmd = new LigneCmd();
            ligneCmd.setProduit(produit);
            ligneCmd.setQtt(ligneCmdDTO.getQtt());
            ligneCmd.setGrammage(prix.getGrammage());
            ligneCmd.setPrix(prixApresRemise);
            ligneCmd.setCommande(commande);

            commande.getLignesCommande().add(ligneCmd);
        }
        Commande savedCommande = CR.saveAndFlush(commande);
        ES.sendEmailToAdmin(savedCommande);
        return savedCommande;
    }

    public List<CommandeDTO.CommandeResponseDTO> getAllCommande() {
        List<Commande> commandes = CR.findAll();
        return commandes.stream().map(CommandeDTO.CommandeResponseDTO::convertToDto)
                .collect(Collectors.toList());
    }

    public Commande getById(Integer id) {
        Optional<Commande> optionalArticle = CR.findById(id);
        return optionalArticle.orElseThrow(() -> new EntityNotFoundException("Command with ID " + id + " does not exist."));
    }

    public void delete(Integer id) {
        Commande existingCommande = this.getById(id);
        CR.deleteById(existingCommande.getId());
    }
}
