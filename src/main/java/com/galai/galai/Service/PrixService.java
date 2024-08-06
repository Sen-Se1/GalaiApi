package com.galai.galai.Service;

import com.galai.galai.Entity.Prix;
import com.galai.galai.Repository.PrixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrixService {
    private final PrixRepository prixRepository;

    public Prix save(Prix prix) {
        return prixRepository.saveAndFlush(prix);
    }

    public void deleteById(Integer id) {
        prixRepository.deleteById(id);
    }
}