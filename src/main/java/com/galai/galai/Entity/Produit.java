package com.galai.galai.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Prix> prixList;

    @Column(nullable = false)
    @NotNull(message = "La description du produit ne peut pas être vide")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "La quantité du produit ne peut pas être vide")
    private Integer Qtt;

    @ElementCollection
    @Column(name = "photos")
    private List<byte[]> photos;

    @Column(nullable = false)
    @NotNull(message = "La vignette du produit ne peut pas être vide")
    private byte[] thumbnail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    @Lob
    @JsonIgnore
    private Categorie categorie;

    private Integer remise = 0;
}