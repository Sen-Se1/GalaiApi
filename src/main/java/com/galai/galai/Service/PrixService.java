package com.galai.galai.Service;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Repository.PrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrixService {
    private final PrixRepository prixRepository;

    @Autowired
    public PrixService(PrixRepository prixRepository) {
        this.prixRepository = prixRepository;
    }

    public Prix save(Prix prix) {
        return prixRepository.saveAndFlush(prix);
    }

    public void deleteById(Integer id) {
        prixRepository.deleteById(id);
    }


}