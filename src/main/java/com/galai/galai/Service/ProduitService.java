package com.galai.galai.Service;

import com.galai.galai.Entity.Categorie;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository PR;
    private final PrixService PS;
    private final CategorieService CS;

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

    public Produit saveProductWithPhotos(String nom, String description, Integer qtt, MultipartFile thumbnail, List<MultipartFile> photos, Integer remise, List<Prix> prixList, Categorie categorie) throws IOException {
        Produit produit = new Produit();
        produit.setNom(nom);
        produit.setDescription(description);
        produit.setQtt(qtt);
        produit.setRemise(remise);
        produit.setThumbnail(thumbnail.getBytes());

        // Convert MultipartFile list to byte array list with exception handling
        List<byte[]> photoBytes = new ArrayList<>();
        for (MultipartFile photo : photos) {
            try {
                photoBytes.add(photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing photo: " + photo.getOriginalFilename(), e);
            }
        }

        produit.setPhotos(photoBytes);
        produit.setPrixList(prixList);
        produit.setCategorie(categorie);
        Produit savedProduit = this.save(produit);


        categorie.getProduits().add(savedProduit);
        CS.save(categorie);

        return savedProduit;
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

    public List<Produit> getAllProduit() {
        return PR.findAll();
    }

    public Produit getProduitById(Integer id) {

        Optional<Produit> optionalArticle = PR.findById(id);
        return optionalArticle.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " does not exist."));
    }

    public Produit updateProduit(Integer id, Produit updatedProduit) {
        Produit existingProduit = PR.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        existingProduit.setNom(updatedProduit.getNom());
        existingProduit.setDescription(updatedProduit.getDescription());
        existingProduit.setQtt(updatedProduit.getQtt());
        existingProduit.setRemise(updatedProduit.getRemise());

        byte[] newThumbnail = updatedProduit.getThumbnail();
        if (newThumbnail != null && newThumbnail.length > 0) {
            existingProduit.setThumbnail(newThumbnail);
        }

        List<byte[]> newPhotos = updatedProduit.getPhotos();
        if (newPhotos != null && !newPhotos.isEmpty()) {
            existingProduit.setPhotos(updatedProduit.getPhotos());
        }

        if (updatedProduit.getPrixList() != null) {
            existingProduit.getPrixList().clear();
            for (Prix newPrix : updatedProduit.getPrixList()) {
                newPrix.setProduit(existingProduit);
                existingProduit.getPrixList().add(newPrix);
            }
        }

        if (updatedProduit.getCategorie() != null) {
            existingProduit.setCategorie(updatedProduit.getCategorie());
        }
        return PR.saveAndFlush(existingProduit);
    }
//public Produit updateProduit(Integer id, Produit updatedProduit) {
//    Produit existingProduit = PR.findById(id)
//            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//
//    existingProduit.setNom(updatedProduit.getNom());
//    existingProduit.setDescription(updatedProduit.getDescription());
//    existingProduit.setQtt(updatedProduit.getQtt());
//    existingProduit.setRemise(updatedProduit.getRemise());
//
//    byte[] newThumbnail = updatedProduit.getThumbnail();
//    if (newThumbnail != null && newThumbnail.length > 0) {
//        existingProduit.setThumbnail(newThumbnail);
//    }
//
//    List<byte[]> newPhotos = updatedProduit.getPhotos();
//    if (newPhotos != null && !newPhotos.isEmpty()) {
//        existingProduit.setPhotos(newPhotos);
//    }
//
//    if (updatedProduit.getPrixList() != null) {
//        existingProduit.getPrixList().clear();
//        for (Prix newPrix : updatedProduit.getPrixList()) {
//            newPrix.setProduit(existingProduit);
//            existingProduit.getPrixList().add(newPrix);
//        }
//    }
//
//    if (updatedProduit.getCategorie() != null) {
//        existingProduit.setCategorie(updatedProduit.getCategorie());
//    }
//
//    return PR.saveAndFlush(existingProduit);
//}

    public void delete(Integer id) {
        PR.deleteById(id);
    }

    public void removePrixFromProduit(Integer produitId, Integer prixId) {
        Produit produit = getProduitById(produitId);
        produit.getPrixList().removeIf(prix -> prix.getId().equals(prixId));
        save(produit);
    }
}

