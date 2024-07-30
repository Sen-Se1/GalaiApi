package com.galai.galai.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique=true)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prix> prixList;

    @Column(nullable = false)
    @NotNull(message = "La description du produit ne peut pas être vide")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "La quantité du produit ne peut pas être vide")
    private Integer Qtt;

    @Column(nullable = false)
    @NotNull(message = "La photo du produit ne peut pas être vide")
    private String photo;
    private Integer remise = 0;


}
