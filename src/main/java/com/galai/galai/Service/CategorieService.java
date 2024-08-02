package com.galai.galai.Service;

import com.galai.galai.Entity.Categorie;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Repository.CategorieRepository;
import com.galai.galai.Repository.PrixRepository;
import com.galai.galai.Repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository CR;
    private final ProduitRepository PR;
    private final PrixRepository PX;

    public Categorie save(Categorie categorie) {
        return CR.saveAndFlush(categorie);
    }

    public List<Categorie> getAllCategorie() {
        return CR.findAll();
    }

    public Categorie getCategorieById(Integer id) {
        Optional<Categorie> optionalCategorie = CR.findById(id);
        return optionalCategorie.orElseThrow(() -> new EntityNotFoundException("Categorie not found"));
    }

    public Categorie updateCategorie(Integer id, Categorie updatedCategorie) {
        Categorie existingCategorie = this.getCategorieById(id);
        existingCategorie.setNom(updatedCategorie.getNom());
        return this.save(existingCategorie);
    }

    public void delete(Integer id) {
        Categorie existingCategorie = this.getCategorieById(id);
        List<Produit> produits = existingCategorie.getProduits();
        for (Produit produit : produits) {
            PX.deleteAll(produit.getPrixList());
            PR.delete(produit);
        }
        CR.deleteById(existingCategorie.getId());
    }
}