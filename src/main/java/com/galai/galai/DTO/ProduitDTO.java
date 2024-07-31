package com.galai.galai.DTO;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Entity.Produit;

import java.util.Base64;
import java.util.List;

public class ProduitDTO {
    private Integer id;
    private String nom;
    private String description;
    private Integer qtt;
    private Integer remise;
    private String photo;
    private List<Prix> prixList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQtt() {
        return qtt;
    }

    public void setQtt(Integer qtt) {
        this.qtt = qtt;
    }

    public Integer getRemise() {
        return remise;
    }

    public void setRemise(Integer remise) {
        this.remise = remise;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Prix> getPrixList() {
        return prixList;
    }

    public void setPrixList(List<Prix> prixList) {
        this.prixList = prixList;
    }

    public static ProduitDTO convertToDto(Produit produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());
        produitDTO.setDescription(produit.getDescription());
        produitDTO.setQtt(produit.getQtt());
        produitDTO.setRemise(produit.getRemise());
        produitDTO.setPrixList(produit.getPrixList());
        if (produit.getPhoto() != null) {
            produitDTO.setPhoto(Base64.getEncoder().encodeToString(produit.getPhoto()));
        }
        return produitDTO;
    }
}
