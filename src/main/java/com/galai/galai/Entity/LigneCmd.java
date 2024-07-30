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
    private String nomProduit;
    private int qte;
    private Double grammage;
    private Double prix;

   @ManyToOne
   @JoinColumn(name = "id_commande", referencedColumnName = "id")
   @JsonIgnore
   private Commande commande;
}
