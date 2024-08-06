package com.galai.galai.Repository;

import com.galai.galai.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
