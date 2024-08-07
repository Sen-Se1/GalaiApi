package com.galai.galai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneCmdResponseDTO {
    private ProduitDTO.ProduitForCommandeDTO produit;
    private Double prix;
    private Double grammage;
    private int qtt;
}
