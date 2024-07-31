package com.galai.galai.Controller;

import com.galai.galai.DTO.ProduitDTO;
import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    public final ProduitService PS;
    public ProduitController(ProduitService ps) {
        PS = ps;
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
    public ResponseEntity<?> save(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("qtt") Integer qtt,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam(value = "remise", required = false, defaultValue = "0") Integer remise) {
        try {
            Produit savedProduit = PS.saveProductWithPhoto(nom, description, qtt, photo, remise);
            return ResponseEntity.ok().body(savedProduit);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            PS.delete(PS.getProduitById(id).getId());
            return ResponseEntity.ok().body("produit : " + id + " supprimée avec succées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }





//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllProduit() {
//        try {
//            return ResponseEntity.ok().body(PS.getAllProduit());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/getById/{id}")
//    public ResponseEntity<?> getProduitByid(@PathVariable("id") Integer id) {
//        try {
//            return ResponseEntity.ok().body(PS.getProduitById(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }





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
    public ResponseEntity<Produit> updateProduit(@PathVariable Integer id, @RequestBody Produit produit) {
        Produit updatedProduit = PS.updateProduit(id, produit);
        return ResponseEntity.ok(updatedProduit);
    }
}
