package com.galai.galai.Controller;

import com.galai.galai.Entity.Produit;
import com.galai.galai.Service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduit() {
        try {
            return ResponseEntity.ok().body(PS.getAllProduit());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getProduitByid(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(PS.getProduitById(id));
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
