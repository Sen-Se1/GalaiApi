package com.galai.galai.DTO;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import lombok.Data;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class ProduitDTO {
    private Integer id;
    private String nom;
    private String description;
    private Integer qtt;
    private Integer remise;
    private String thumbnail;
    private List<String> photos;
    private List<Prix> prixList;

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
        return produitDTO;
    }
}