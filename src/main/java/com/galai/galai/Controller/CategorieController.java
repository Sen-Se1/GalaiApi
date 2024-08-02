package com.galai.galai.Controller;

import com.galai.galai.Entity.Categorie;
import com.galai.galai.Service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorie")
public class CategorieController {
    private final CategorieService CS;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Categorie categorie) {
        try {
            Categorie savedCategorie = CS.save(categorie);
            return ResponseEntity.ok().body(savedCategorie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
    public ResponseEntity<?> updateCategorie(@PathVariable("id") Integer id, @RequestBody Categorie categorie) {
        try {
            System.out.println(categorie);
            Categorie updatedCategorie = CS.updateCategorie(id, categorie);
            return ResponseEntity.ok(updatedCategorie);
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