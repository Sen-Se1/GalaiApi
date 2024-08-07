package com.galai.galai.DTO.Categorie;

import com.galai.galai.Entity.Categorie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieDTO {
    private Integer id;
    private String nom;
    private String description;
    private String photo;
    private List<ProduitForCategorieDTO> produits;

    public static CategorieDTO convertToDto(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(categorie.getId());
        categorieDTO.setNom(categorie.getNom());
        categorieDTO.setDescription(categorie.getDescription());
        if (categorie.getPhoto() != null) {
            categorieDTO.setPhoto(Base64.getEncoder().encodeToString(categorie.getPhoto()));
        }
        categorieDTO.setProduits(categorie.getProduits().stream()
                .map(ProduitForCategorieDTO::convertToDto)
                .collect(Collectors.toList()));
        return categorieDTO;
    }

    public static CategorieDTO convertToDto2(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(categorie.getId());
        categorieDTO.setNom(categorie.getNom());
        categorieDTO.setDescription(categorie.getDescription());
        if (categorie.getPhoto() != null) {
            categorieDTO.setPhoto(Base64.getEncoder().encodeToString(categorie.getPhoto()));
        }
        return categorieDTO;
    }
}
