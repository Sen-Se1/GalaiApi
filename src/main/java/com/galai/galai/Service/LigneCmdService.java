package com.galai.galai.Service;

import com.galai.galai.Entity.LigneCmd;
import com.galai.galai.Repository.LigneCmdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LigneCmdService {
    @Autowired
    private LigneCmdRepository ligneCmdRepository;

    public LigneCmd save(LigneCmd ligneCmd) {
        return ligneCmdRepository.save(ligneCmd);
    }
}

