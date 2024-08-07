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
    private String thumbnail;
    private List<Prix> prixList;
    private CategorieDTO.GetAllCategorieForProdDTO categorie;

    public static ProduitDTO convertToDto(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());

        if (produit.getThumbnail() != null) {
            produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
        }

        produitDTO.setPrixList(produit.getPrixList());

        if (produit.getCategorie() != null) {
            CategorieDTO.GetAllCategorieForProdDTO categorieDTO = new CategorieDTO.GetAllCategorieForProdDTO();
            categorieDTO.setId(produit.getCategorie().getId());
            categorieDTO.setNom(produit.getCategorie().getNom());
            produitDTO.setCategorie(categorieDTO);
        }
        return produitDTO;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllProduitClientDTO {
        private Integer id;
        private String nom;
        private String thumbnail;
        private List<Prix> prixList;

        public static GetAllProduitClientDTO convertToDto(Produit produit) {
            GetAllProduitClientDTO produitDTO = new GetAllProduitClientDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            if (produit.getThumbnail() != null) {
                produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
            }

            produitDTO.setPrixList(produit.getPrixList());
            return produitDTO;
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetByIdProduitAdminDTO {
        private Integer id;
        private String nom;
        private String description;
        private List<Prix> prixList;
        private CategorieDTO.GetAllCategorieForProdDTO categorie;

        public static GetByIdProduitAdminDTO convertToDto(Produit produit) {
            GetByIdProduitAdminDTO produitDTO = new GetByIdProduitAdminDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            produitDTO.setDescription(produit.getDescription());
            produitDTO.setPrixList(produit.getPrixList());

            if (produit.getCategorie() != null) {
                CategorieDTO.GetAllCategorieForProdDTO categorieDTO = new CategorieDTO.GetAllCategorieForProdDTO();
                categorieDTO.setId(produit.getCategorie().getId());
                categorieDTO.setNom(produit.getCategorie().getNom());
                produitDTO.setCategorie(categorieDTO);
            }
            return produitDTO;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetByIdProduitClientDTO {
        private Integer id;
        private String nom;
        private String description;
        private String thumbnail;
        private List<Prix> prixList;
        private List<String> photos;

        public static GetByIdProduitClientDTO convertToDto(Produit produit) {
            GetByIdProduitClientDTO produitDTO = new GetByIdProduitClientDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            produitDTO.setDescription(produit.getDescription());
            produitDTO.setPrixList(produit.getPrixList());
            if (produit.getThumbnail() != null) {
                produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
            }

            if (produit.getPhotos() != null) {
                produitDTO.setPhotos(produit.getPhotos().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo))
                        .collect(Collectors.toList()));
            }
            return produitDTO;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProduitForCategorieDTO {
        private Integer id;
        private String nom;
        private String description;
        private String thumbnail;
        private List<Prix> prixList;

        public static ProduitForCategorieDTO convertToDto(Produit produit) {
            ProduitForCategorieDTO produitDTO = new ProduitForCategorieDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            produitDTO.setDescription(produit.getDescription());
            if (produit.getThumbnail() != null) {
                produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
            }

            produitDTO.setPrixList(produit.getPrixList());
            return produitDTO;
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProduitForCommandeDTO {
        private Integer id;
        private String nom;
    }
}
