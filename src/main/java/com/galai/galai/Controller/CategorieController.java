package com.galai.galai.Controller;

import com.galai.galai.DTO.CategorieDTO;
import com.galai.galai.Entity.Categorie;
import com.galai.galai.Service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorie")
public class CategorieController {
    private final CategorieService CS;

    @PostMapping("/admin/save")
    public ResponseEntity<?> saveWithPhoto(@RequestParam("nom") String nom, @RequestParam("description") String description, @RequestParam("photo") MultipartFile photo) {
        try {
            Categorie savedCategorie = CS.saveCategorieWithPhoto(nom, description, photo);
            return ResponseEntity.ok().body(savedCategorie);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/admin/getAll/withoutProdPhotos")
    public ResponseEntity<?> getAllCategorieWithoutProdPhotos() {
        try {
            List<CategorieDTO.GetAllCategorieWithoutProdPhotosDTO> categories = CS.getAllCategorieWithoutProdPhotos();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/getAllName")
    public ResponseEntity<?> getAllNameCategorie() {
        try {
            List<CategorieDTO.GetAllCategorieNameDTO> categories = CS.getAllNameCategorie();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/getById/withoutProduit/{id}")
    public ResponseEntity<?> getCategorieByIdWithoutProduit(@PathVariable("id") Integer id) {
        try {
            CategorieDTO.GetCategorieByIdWithoutProduitDTO categorieDTO = CS.getCategorieByIdWithoutProduit(id);
            return ResponseEntity.ok().body(categorieDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateProduit(
            @PathVariable Integer id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            Categorie updatedCategorie = new Categorie();
            updatedCategorie.setNom(nom);
            updatedCategorie.setDescription(description);
            updatedCategorie.setPhoto(photo.getBytes());
            Categorie savedCategorie = CS.updateCategorie(id, updatedCategorie);
            return ResponseEntity.ok(CategorieDTO.GetCategorieByIdWithoutProduitDTO.convertToDto(savedCategorie));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            CS.delete(id);
            return ResponseEntity.ok().body("categorie : " + id + " supprimée avec succées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAll/prodsWithThumb")
    public ResponseEntity<?> getAllCategorieProdsWithThumb() {
        try {
            List<CategorieDTO> categories = CS.getAllCategorieProdsWithThumb();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}