package com.galai.galai.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private double prix;

    private double grammage;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;
}
