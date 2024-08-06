package com.galai.galai.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commande")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Column(nullable = false)
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Column(nullable = false)
    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @Column(nullable = false)
    @NotBlank(message = "Le pays est obligatoire")
    private String pays;

    @Column(nullable = false)
    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @Column(nullable = false)
    @NotNull(message = "Le code postal est obligatoire")
    @Min(value = 1000, message = "Le code postal doit avoir au moins 4 chiffres")
    @Max(value = 9999, message = "Le code postal ne peut pas dépasser 5 chiffres")
    private Integer codePostal;

    @Column(nullable = false, length = 8)
    @NotNull(message = "Le numéro de téléphone est obligatoire")
    @Digits(integer = 8, fraction = 0, message = "Le numéro de téléphone doit avoir exactement 8 chiffres")
    private Integer numeroTelephone;

    @Column
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCmd> lignesCommande = new ArrayList<>();

    @Transient
    private Double prixTotal;

    @PrePersist
    protected void onCreate() {
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now().withNano(0);
        }
        calculatePrixTotal();
    }

    @PostLoad
    @PostPersist
    private void calculatePrixTotal() {
        if (lignesCommande != null && !lignesCommande.isEmpty()) {
            prixTotal = lignesCommande.stream()
                    .mapToDouble(ligne -> ligne.getPrix() * ligne.getQtt())
                    .sum() + 7;
        } else {
            prixTotal = 7.0;
        }
    }
}
