package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.entities.CompteMedecin;
import com.yvanscoop.gestcabinet.repositories.CompteMedecinRepository;
import com.yvanscoop.gestcabinet.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompteMedecinService {

    @Autowired
    private CompteMedecinRepository compteMedecinRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    public CompteMedecin addCompte(CompteMedecin cmpte){
        return compteMedecinRepository.save(cmpte);
    }

    public boolean loginExist(String login){
        return compteMedecinRepository.existsCompteMedecinByLogin(login);
    }

}
