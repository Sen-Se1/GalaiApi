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
    private CategorieDTO.GetAllCategorieNameDTO categorie;

    public static ProduitDTO convertToDto(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());

        if (produit.getThumbnail() != null) {
            produitDTO.setThumbnail(Base64.getEncoder().encodeToString(produit.getThumbnail()));
        }

        produitDTO.setPrixList(produit.getPrixList());

        if (produit.getCategorie() != null) {
            CategorieDTO.GetAllCategorieNameDTO categorieDTO = new CategorieDTO.GetAllCategorieNameDTO();
            categorieDTO.setId(produit.getCategorie().getId());
            categorieDTO.setNom(produit.getCategorie().getNom());
            produitDTO.setCategorie(categorieDTO);
        }
        return produitDTO;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllProduitWithoutCategorieAndPhotosDTO {
        private Integer id;
        private String nom;
        private String thumbnail;
        private List<Prix> prixList;

        public static GetAllProduitWithoutCategorieAndPhotosDTO convertToDto(Produit produit) {
            GetAllProduitWithoutCategorieAndPhotosDTO produitDTO = new GetAllProduitWithoutCategorieAndPhotosDTO();
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
    public static class GetByIdProduitWithCategorieNameWithoutFilesDTO {
        private Integer id;
        private String nom;
        private String description;
        private List<Prix> prixList;
        private CategorieDTO.GetAllCategorieNameDTO categorie;

        public static GetByIdProduitWithCategorieNameWithoutFilesDTO convertToDto(Produit produit) {
            GetByIdProduitWithCategorieNameWithoutFilesDTO produitDTO = new GetByIdProduitWithCategorieNameWithoutFilesDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            produitDTO.setDescription(produit.getDescription());
            produitDTO.setPrixList(produit.getPrixList());

            if (produit.getCategorie() != null) {
                CategorieDTO.GetAllCategorieNameDTO categorieDTO = new CategorieDTO.GetAllCategorieNameDTO();
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
    public static class GetProduitNameDTO {
        private Integer id;
        private String nom;

        public static GetProduitNameDTO convertToDto(Produit produit) {
            GetProduitNameDTO produitDTO = new GetProduitNameDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());
            return produitDTO;
        }
    }
}
