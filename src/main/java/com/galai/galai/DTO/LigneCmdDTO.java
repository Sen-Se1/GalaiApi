package com.galai.galai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneCmdDTO {
    private Integer produitId;
    private Integer prixId;
    private int Qtt;
}
