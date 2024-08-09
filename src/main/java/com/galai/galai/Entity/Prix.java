package com.galai.galai.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prix {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Le prix du produit ne peut pas être vide")
    private double prix;

    @Column(nullable = false)
    @NotNull(message = "La grammage du produit ne peut pas être vide")
    private double grammage;

    @Column(nullable = false)
    @NotNull(message = "La quantité du produit ne peut pas être vide")
    private Integer qtt;

    private Integer remise = 0;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;
}
