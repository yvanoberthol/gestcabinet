package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.MedecinSpecialite;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.repositories.MedecinSpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedecinSpecialiteService {

    @Autowired
    private MedecinSpecialiteRepository msRepository;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private MedecinService medecinService;


    public int countBySpecialite(Specialite specialite) {
        return msRepository.countBySpecialite(specialite);
    }

    public MedecinSpecialite findMedecinSpecialite(String specialiteName, String matricule) {
        Specialite specialite = specialiteService.getByNom(specialiteName);
        Medecin medecin = medecinService.getByMatricule(matricule);
        return msRepository.findBySpecialiteMedecin(specialite, medecin);
    }

    public List<MedecinSpecialite> findBySpecialite(Specialite specialite) {
        return msRepository.findByDomaine(specialite);
    }


    public MedecinSpecialite findByMedecin(Medecin medecin) {
        return msRepository.findOneByMedecin(medecin);
    }
}
