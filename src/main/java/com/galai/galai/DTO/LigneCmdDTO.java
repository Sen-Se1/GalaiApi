package com.galai.galai.DTO;

import com.galai.galai.Entity.LigneCmd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneCmdDTO {
    private Integer id;
    private Integer produitId;
    private Integer prixId;
    private int Qtt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LigneCmdResponseDTO {
        private Integer id;
        private ProduitDTO.GetProduitNameDTO produit;
        private Double prix;
        private Double grammage;
        private int qtt;

        public static LigneCmdResponseDTO convertToDto(LigneCmd ligneCmd) {
            return new LigneCmdResponseDTO(
                    ligneCmd.getId(),
                    ProduitDTO.GetProduitNameDTO.convertToDto(ligneCmd.getProduit()),
                    ligneCmd.getPrix(),
                    ligneCmd.getGrammage(),
                    ligneCmd.getQtt());

        }
    }
}
