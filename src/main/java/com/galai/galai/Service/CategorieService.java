package com.galai.galai.Service;

import com.galai.galai.DTO.CategorieDTO;
import com.galai.galai.Entity.Categorie;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Repository.CategorieRepository;
import com.galai.galai.Repository.PrixRepository;
import com.galai.galai.Repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository CR;
    private final ProduitRepository PR;
    private final PrixRepository PX;

    public Categorie save(Categorie categorie) {
        return CR.saveAndFlush(categorie);
    }

    public Categorie saveCategorieWithPhoto(String nom, String description, MultipartFile photo) throws IOException {
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorie.setDescription(description);
        categorie.setPhoto(photo.getBytes());
        return this.save(categorie);
    }

    public List<CategorieDTO> getAllCategorieProdsWithThumb() {
        List<Categorie> categories = CR.findAll();
        return categories.stream()
                .map(CategorieDTO::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CategorieDTO.GetAllCategorieWithoutProdPhotosDTO> getAllCategorieWithoutProdPhotos() {
        List<Categorie> categories = CR.findAll();
        return categories.stream()
                .map(CategorieDTO.GetAllCategorieWithoutProdPhotosDTO::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CategorieDTO.GetAllCategorieNameDTO> getAllNameCategorie() {
        List<Categorie> categories = CR.findAll();
        return categories.stream()
                .map(CategorieDTO.GetAllCategorieNameDTO::convertToDto)
                .collect(Collectors.toList());
    }

    public Categorie getExistingCategorieById(Integer id) {
        Optional<Categorie> optionalCategorie = CR.findById(id);
        return optionalCategorie.orElseThrow(() -> new EntityNotFoundException("Categorie with ID " + id + " does not exist."));
    }

    public CategorieDTO.GetCategorieByIdWithoutProduitDTO getCategorieByIdWithoutProduit(Integer id) {
        Categorie categorie = this.getExistingCategorieById(id);
        return CategorieDTO.GetCategorieByIdWithoutProduitDTO.convertToDto(categorie);
    }

    public Categorie updateCategorie(Integer id, Categorie updatedCategorie) {
        Categorie existingCategorie = this.getExistingCategorieById(id);
        existingCategorie.setNom(updatedCategorie.getNom());
        existingCategorie.setDescription(updatedCategorie.getDescription());
        byte[] newPhoto = updatedCategorie.getPhoto();
        if (newPhoto != null && newPhoto.length > 0) {
            existingCategorie.setPhoto(newPhoto);
        }

        return this.save(existingCategorie);
    }

    public void delete(Integer id) {
        Categorie existingCategorie = this.getExistingCategorieById(id);
        List<Produit> produits = existingCategorie.getProduits();
        for (Produit produit : produits) {
            PX.deleteAll(produit.getPrixList());
            PR.delete(produit);
        }
        CR.deleteById(existingCategorie.getId());
    }
}