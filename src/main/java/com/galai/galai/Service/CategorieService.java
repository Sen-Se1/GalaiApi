package com.galai.galai.Service;

import com.galai.galai.Entity.Categorie;
import com.galai.galai.Repository.CategorieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    @Autowired
    private final CategorieRepository CR;

    public CategorieService(CategorieRepository cr) {
        CR = cr;
    }

    public Categorie save(Categorie categorie) {
        return CR.saveAndFlush(categorie);
    }

    public List<Categorie> getAllCategorie() {
        return CR.findAll();
    }

    public Categorie getCategorieById(Integer id) {
        Optional<Categorie> optionalCategorie = CR.findById(id);
        return optionalCategorie.orElseThrow(() ->new EntityNotFoundException("Categorie not found"));
    }

    public Categorie updateCategorie(Integer id, Categorie updatedCategorie) {
        Categorie existingCategorie = this.getCategorieById(id);
        existingCategorie.setNom(updatedCategorie.getNom());
        return this.save(existingCategorie);
    }

    public void delete(Integer id) {
        Categorie existingCategorie = this.getCategorieById(id);
        CR.deleteById(existingCategorie.getId());
    }
}
