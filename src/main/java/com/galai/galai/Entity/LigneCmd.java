package com.galai.galai.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
 public class LigneCmd {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;

    private Integer qtt;
    private Double grammage;
    private Double prix;

   @ManyToOne
   @JoinColumn(name = "id_commande", referencedColumnName = "id")
   @JsonIgnore
   private Commande commande;
}
