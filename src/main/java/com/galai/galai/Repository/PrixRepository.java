package com.galai.galai.Repository;

import com.galai.galai.Entity.Prix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrixRepository extends JpaRepository<Prix, Integer> {
}
