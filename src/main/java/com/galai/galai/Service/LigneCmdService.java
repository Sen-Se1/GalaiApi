package com.galai.galai.Service;

import com.galai.galai.Entity.LigneCmd;
import com.galai.galai.Repository.LigneCmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LigneCmdService {
    private LigneCmdRepository ligneCmdRepository;

    public LigneCmd save(LigneCmd ligneCmd) {
        return ligneCmdRepository.save(ligneCmd);
    }
}

