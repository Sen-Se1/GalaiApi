package com.galai.galai.DTO;

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
    private List<ProduitDTO.ProduitForCategorieDTO> produits;

    public static CategorieDTO convertToDto(Categorie categorie) {
        CategorieDTO categorieDTO = new CategorieDTO();
        categorieDTO.setId(categorie.getId());
        categorieDTO.setNom(categorie.getNom());
        categorieDTO.setDescription(categorie.getDescription());
        if (categorie.getPhoto() != null) {
            categorieDTO.setPhoto(Base64.getEncoder().encodeToString(categorie.getPhoto()));
        }
        categorieDTO.setProduits(categorie.getProduits().stream()
                .map(ProduitDTO.ProduitForCategorieDTO::convertToDto)
                .collect(Collectors.toList()));
        return categorieDTO;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllCategorieWithoutProdFileDTO {
        private Integer id;
        private String nom;
        private String description;
        private String photo;
        private Integer nbProduit;

        public static GetAllCategorieWithoutProdFileDTO convertToDto(Categorie categorie) {
            GetAllCategorieWithoutProdFileDTO categorieDTO = new GetAllCategorieWithoutProdFileDTO();
            categorieDTO.setId(categorie.getId());
            categorieDTO.setNom(categorie.getNom());
            categorieDTO.setDescription(categorie.getDescription());
            if (categorie.getPhoto() != null) {
                categorieDTO.setPhoto(Base64.getEncoder().encodeToString(categorie.getPhoto()));
            }
            categorieDTO.setNbProduit(categorie.getProduits().size());
            return categorieDTO;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetByCategorieIdDTO {
        private Integer id;
        private String nom;
        private String description;
        private String photo;

        public static GetByCategorieIdDTO convertToDto(Categorie categorie) {
            GetByCategorieIdDTO categorieDTO = new GetByCategorieIdDTO();
            categorieDTO.setId(categorie.getId());
            categorieDTO.setNom(categorie.getNom());
            categorieDTO.setDescription(categorie.getDescription());
            if (categorie.getPhoto() != null) {
                categorieDTO.setPhoto(Base64.getEncoder().encodeToString(categorie.getPhoto()));
            }
            return categorieDTO;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllCategorieForProdDTO {
        private Integer id;
        private String nom;

        public static GetAllCategorieForProdDTO convertToDto(Categorie categorie) {
            GetAllCategorieForProdDTO categorieDTO = new GetAllCategorieForProdDTO();
            categorieDTO.setId(categorie.getId());
            categorieDTO.setNom(categorie.getNom());
            return categorieDTO;
        }
    }

}
