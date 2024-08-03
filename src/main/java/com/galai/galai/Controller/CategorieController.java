package com.galai.galai.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galai.galai.Entity.Categorie;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorie")
public class CategorieController {
    private final CategorieService CS;

    @PostMapping("/save")
    public ResponseEntity<?> saveWithPhoto(@RequestParam("nom") String nom, @RequestParam("description") String description, @RequestParam("photo") MultipartFile photo) {
        try {
            Categorie savedCategorie = CS.saveCategorieWithPhoto(nom, description, photo);
            return ResponseEntity.ok().body(savedCategorie);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategorie() {
        try {
            List<Categorie> categories = CS.getAllCategorie();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCategorieById(@PathVariable("id") Integer id) {
        try {
            Categorie categorie = CS.getCategorieById(id);
            return ResponseEntity.ok().body(categorie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
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
            return ResponseEntity.ok(savedCategorie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            CS.delete(id);
            return ResponseEntity.ok().body("categorie : " + id + " supprimée avec succées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}