package com.galai.galai.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galai.galai.DTO.ProduitDTO;
import com.galai.galai.Entity.Categorie;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.CategorieService;
import com.galai.galai.Service.PrixService;
import com.galai.galai.Service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/produit")
public class ProduitController {
    private final ProduitService PS;
    private final PrixService PrS;
    private final CategorieService CS;

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveWithPhotos(@RequestParam("nom") String nom, @RequestParam("description") String description, @RequestParam("categorieId") Integer categorieId, @RequestParam("prixList") String prixListJson, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam("photos") List<MultipartFile> photos) {
        try {
            // Convert JSON string to List<Prix>
            ObjectMapper mapper = new ObjectMapper();
            List<Prix> prixList = null;
            if (!prixListJson.isEmpty()) {
                prixList = mapper.readValue(prixListJson, new TypeReference<>() {
                });
            }
            Categorie categorie = CS.getExistingCategorieById(categorieId);
            Produit savedProduit = PS.saveProductWithPhotos(nom, description, thumbnail, photos, prixList, categorie);
            return ResponseEntity.ok().body(savedProduit);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/admin/getAll/CategorieName/withoutPhotos")
    public ResponseEntity<?> getAllProduitWithCategorieNameWithoutPhoto() {
        try {
            List<ProduitDTO> produits = PS.getAllProduitWithCategorieNameWithoutPhoto();
            return ResponseEntity.ok().body(produits);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/getById/CategorieName/withoutFiles/{id}")
    public ResponseEntity<?> getProduitByIdWithCategorieNameWithoutFiles(@PathVariable("id") Integer id) {
        try {
            ProduitDTO.GetByIdProduitWithCategorieNameWithoutFilesDTO produitDTO = PS.getProduitByIdWithCategorieNameWithoutFiles(id);
            return ResponseEntity.ok().body(produitDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateProduit(
            @PathVariable Integer id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "photos", required = false) List<MultipartFile> photos,
            @RequestParam(value = "prixList", required = false) String prixListJson,
            @RequestParam(value = "categorieId", required = false) Integer categorieId) {

        try {
            // Convert JSON string to List<Prix>
            ObjectMapper mapper = new ObjectMapper();
            List<Prix> prixList = null;
            if (prixListJson != null && !prixListJson.isEmpty()) {
                prixList = mapper.readValue(prixListJson, new TypeReference<>() {
                });
            }

            // Create updated Produit object
            Produit updatedProduit = new Produit();
            updatedProduit.setNom(nom);
            updatedProduit.setDescription(description);
            updatedProduit.setThumbnail(thumbnail.getBytes());

            // Convert MultipartFile list to byte array list with exception handling
            List<byte[]> photoBytes = new ArrayList<>();
            if (photos != null && !photos.isEmpty()) {
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) { // Check if the file is not empty
                        photoBytes.add(photo.getBytes());
                    }
                }
            }

            updatedProduit.setPhotos(photoBytes);
            updatedProduit.setPrixList(prixList);

            Categorie categorie = CS.getExistingCategorieById(categorieId);
            updatedProduit.setCategorie(categorie);

            Produit savedProduit = PS.updateProduit(id, updatedProduit);
            return ResponseEntity.ok(savedProduit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating produit: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            PS.delete(id);
            return ResponseEntity.ok().body("produit : " + id + " supprimée avec succées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/prix/delete/{id}/{produitId}")
    public ResponseEntity<?> deletePrix(@PathVariable("id") Integer id, @PathVariable("produitId") Integer produitId) {
        try {
            PS.removePrixFromProduit(produitId, id);
            PrS.deleteById(id);
            return ResponseEntity.ok().body("Prix : " + id + " supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAll/withoutCategorie/withoutPhotos")
    public ResponseEntity<?> getAllProduitWithoutCategorieAndPhotos() {
        try {
            List<ProduitDTO.GetAllProduitWithoutCategorieAndPhotosDTO> produits = PS.getAllProduitWithoutCategorieAndPhotos();
            return ResponseEntity.ok().body(produits);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById/withoutCategorie/{id}")
    public ResponseEntity<?> getProduitByIdWithoutCategorie(@PathVariable("id") Integer id) {
        try {
            ProduitDTO.GetByIdProduitClientDTO produitDTO = PS.getProduitByIdWithoutCategorie(id);
            return ResponseEntity.ok().body(produitDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
