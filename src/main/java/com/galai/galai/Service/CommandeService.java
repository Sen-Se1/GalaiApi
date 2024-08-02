package com.galai.galai.Service;

import com.galai.galai.Entity.Commande;
import com.galai.galai.Entity.LigneCmd;
import com.galai.galai.Repository.CommandeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeRepository CR;

    public List<Commande> getAllCommande() {
        return CR.findAll();
    }
    public Commande getById( Integer id){
  Optional<Commande> optionalArticle = CR.findById(id);
  return optionalArticle.orElseThrow(() ->new EntityNotFoundException("Command not found"));
}

    public Commande getCommandeByNom(String nom) {
        Optional<Commande> optionalCommande = CR.findByNom(nom);
        return optionalCommande.orElseThrow(() -> new EntityNotFoundException("Command not found with name " + nom ));
    }

    public Commande save(Commande commande) {
        return CR.saveAndFlush(commande);
    }

    public void delete(Integer id) {
        CR.deleteById(id);
    }



}
