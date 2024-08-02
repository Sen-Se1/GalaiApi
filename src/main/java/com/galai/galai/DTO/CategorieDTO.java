package com.galai.galai.DTO;

import com.galai.galai.Entity.Categorie;
import lombok.Data;

@Data
public class CategorieDTO {
    private Integer id;
    private String nom;

    public static CategorieDTO convertToDto(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(categorie.getId());
        categorieDTO.setNom(categorie.getNom());
        return categorieDTO;
    }
}