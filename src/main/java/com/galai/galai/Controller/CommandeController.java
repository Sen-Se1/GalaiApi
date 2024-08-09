package com.galai.galai.Controller;

import com.galai.galai.DTO.*;
import com.galai.galai.Entity.*;
import com.galai.galai.Service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("commande")
public class CommandeController {
    private final CommandeService CS;

    @GetMapping("/admin/getAll")
    public ResponseEntity<?> getAllCommande() {
        try {
            List<CommandeDTO.CommandeResponseDTO> commandes = CS.getAllCommande();
            return ResponseEntity.ok().body(commandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            CS.delete(id);
            return ResponseEntity.ok().body("Commande : " + id + " supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CommandeDTO commandeDTO) {
        try {
            Commande savedCommande = CS.save(commandeDTO);
            return ResponseEntity.ok().body(CommandeDTO.CommandeResponseDTO.convertToDto(savedCommande));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getCount")
    public ResponseEntity<?> getCount() {
        try {
            Long countCommande = CS.getCountCommande();
            return ResponseEntity.ok().body(countCommande);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
