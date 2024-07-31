package com.galai.galai.Service;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    @Autowired
    private final ProduitRepository PR;
    private final PrixService PS;
    public ProduitService(ProduitRepository pr, PrixService ps) {
        PR = pr;
        PS = ps;
    }


    public Produit save(Produit produit) {
        Produit savedProduit = PR.saveAndFlush(produit);
        if (produit.getPrixList() != null) {
            for (Prix prix : produit.getPrixList()) {
                prix.setProduit(savedProduit);
                PS.save(prix);
            }
        }
        return savedProduit;
    }

    public Produit saveProductWithPhoto(String nom, String description, Integer qtt, MultipartFile photo, Integer remise) throws IOException {
        Produit produit = new Produit();
        produit.setNom(nom);
        produit.setDescription(description);
        produit.setQtt(qtt);
        produit.setPhoto(photo.getBytes());
        produit.setRemise(remise);
        return save(produit);
    }

    public List<Produit> saveAll(List<Produit> articles) {
        for (Produit produit : articles) {
            Produit savedProduit = PR.saveAndFlush(produit);
            if (produit.getPrixList() != null) {
                for (Prix prix : produit.getPrixList()) {
                    prix.setProduit(savedProduit);
                    PS.save(prix);
                }
            }
        }
        return articles;
    }

    public void delete(Integer id) {
        PR.deleteById(id);
    }

    public List<Produit> getAllProduit() {
        return PR.findAll();
    }

    public Produit getProduitById(Integer id) {

        Optional<Produit> optionalArticle = PR.findById(id);
        return optionalArticle.orElseThrow(() ->new EntityNotFoundException("Product not found"));
    }
    public Produit updateProduit(Integer id, Produit updatedProduit) {
        Produit existingProduit = PR.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        existingProduit.setNom(updatedProduit.getNom());
        existingProduit.setDescription(updatedProduit.getDescription());
        existingProduit.setQtt(updatedProduit.getQtt());
        existingProduit.setRemise(updatedProduit.getRemise());
        existingProduit.setPhoto(updatedProduit.getPhoto());
        if (updatedProduit.getPrixList() != null) {
            for (Prix newPrix : updatedProduit.getPrixList()) {
                newPrix.setProduit(existingProduit);
                existingProduit.getPrixList().add(newPrix);
            }
        }

        Produit savedProduit = PR.saveAndFlush(existingProduit);

        if (updatedProduit.getPrixList() != null) {
            for (Prix prix : updatedProduit.getPrixList()) {
                prix.setProduit(savedProduit);
                PS.save(prix);
            }
        }

        return savedProduit;
    }
}

