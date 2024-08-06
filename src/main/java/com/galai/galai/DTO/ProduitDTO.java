package com.galai.galai.DTO;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDTO {
    private Integer id;
    private String nom;
    private String description;
    private int qtt;
    private double remise;
    private String thumbnail;
    private List<String> photos;
    private List<Prix> prixList;
    private CategorieDTO categorie;

    public static ProduitDTO convertToDto(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());
        produitDTO.setDescription(produit.getDescription());
        produitDTO.setQtt(produit.getQtt());
        produitDTO.setRemise(produit.getRemise());
        if (produit.getThumbnail() != null) {
            produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
        }


        if (produit.getPhotos() != null) {
            produitDTO.setPhotos(produit.getPhotos().stream()
                    .map(photo -> Base64.getEncoder().encodeToString(photo))
                    .collect(Collectors.toList()));
        }
        produitDTO.setPrixList(produit.getPrixList());

        if (produit.getCategorie() != null) {
            CategorieDTO categorieDTO = new CategorieDTO();
            categorieDTO.setId(produit.getCategorie().getId());
            categorieDTO.setNom(produit.getCategorie().getNom());
            categorieDTO.setDescription(produit.getCategorie().getDescription());

            if (produit.getCategorie().getPhoto() != null) {
                categorieDTO.setPhoto(Base64.getEncoder().encodeToString(produit.getCategorie().getPhoto()));
            }

            produitDTO.setCategorie(categorieDTO);
        }
        return produitDTO;
    }
}
