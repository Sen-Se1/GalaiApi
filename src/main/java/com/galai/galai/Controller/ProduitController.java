package com.galai.galai.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galai.galai.DTO.ProduitDTO;
import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.PrixService;
import com.galai.galai.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    public final ProduitService PS;
    public final PrixService PrS;
    public ProduitController(ProduitService ps, PrixService prS) {
        PS = ps;
        PrS = prS;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Produit produit) {
        System.out.println(produit);
        try {
            Produit savedProduit = PS.save(produit);
            return ResponseEntity.ok().body(savedProduit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save2")
    public ResponseEntity<?> saveWithPhotos(@RequestParam("nom") String nom, @RequestParam("description") String description, @RequestParam("qtt") Integer qtt, @RequestParam(value = "remise", required = false, defaultValue = "0") Integer remise, @RequestParam("prixList") String prixListJson, @RequestParam("thumbnail") MultipartFile thumbnail, @RequestParam("photos") List<MultipartFile> photos) {
        try {
            // Convert JSON string to List<Prix>
            ObjectMapper mapper = new ObjectMapper();
            List<Prix> prixList = null;
            if (!prixListJson.isEmpty()) {
                prixList = mapper.readValue(prixListJson, new TypeReference<List<Prix>>() {});
            }

            Produit savedProduit = PS.saveProductWithPhotos(nom, description, qtt, thumbnail, photos, remise, prixList);
            return ResponseEntity.ok().body(savedProduit);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<?> saveAll(@RequestBody List<Produit> produit) {
        try {
            return ResponseEntity.ok().body(PS.saveAll(produit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduit() {
        try {
            List<Produit> produits = PS.getAllProduit();
            List<ProduitDTO> produitsDTO = produits.stream()
                    .map(ProduitDTO::convertToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(produitsDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getProduitById(@PathVariable("id") Integer id) {
        try {
            Produit produit = PS.getProduitById(id);
            ProduitDTO produitDTO = ProduitDTO.convertToDto(produit);
            return ResponseEntity.ok().body(produitDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable Integer id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("qtt") Integer qtt,
            @RequestParam(value = "remise", required = false, defaultValue = "0") Integer remise,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestParam(value = "photos", required = false) List<MultipartFile> photos,
            @RequestParam(value = "prixList", required = false) String prixListJson) {

        try {
            // Convert JSON string to List<Prix>
            ObjectMapper mapper = new ObjectMapper();
            List<Prix> prixList = null;
            if (prixListJson != null && !prixListJson.isEmpty()) {
                prixList = mapper.readValue(prixListJson, new TypeReference<List<Prix>>() {});
            }

            // Create updated Produit object
            Produit updatedProduit = new Produit();
            updatedProduit.setNom(nom);
            updatedProduit.setDescription(description);
            updatedProduit.setQtt(qtt);
            updatedProduit.setRemise(remise);
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

            Produit savedProduit = PS.updateProduit(id, updatedProduit);
            return ResponseEntity.ok(savedProduit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            PS.delete(PS.getProduitById(id).getId());
            return ResponseEntity.ok().body("produit : " + id + " supprimée avec succées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/prix/delete/{id}/{produitId}")
    public ResponseEntity<?> deletePrix(@PathVariable("id") Integer id, @PathVariable("produitId") Integer produitId) {
        try {
            PS.removePrixFromProduit(produitId, id);
            PrS.deleteById(id);
            return ResponseEntity.ok().body("Prix : " + id + " supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
