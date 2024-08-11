package com.galai.galai.Repository;

import com.galai.galai.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
