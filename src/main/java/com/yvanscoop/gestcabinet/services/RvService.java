package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Rv;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.repositories.RvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RvService {

    @Autowired
    private RvRepository rvRepository;

    public List<Rv> getRvMedecinJour(String matricule, Date jour) {
        //DateUtils.gererDate(jour)
        return rvRepository.getRvMedecinJour(matricule, jour);
    }

    public List<Rv> getRvMedecinJourPris(String matricule, Date jour) {
        //DateUtils.gererDate(jour)
        return rvRepository.getRvMedecinJourPris(matricule, jour);
    }

    public boolean add(Date jour, Client client, Creneau creneau, Specialite specialite) throws ParseException {

        Rv rv = new Rv(client,creneau,specialite);
        rv.setJour(jour);
        rv.setAnnule(false);
        rvRepository.save(rv);
        return true;
    }

    public void delete(Long idRv) {
        rvRepository.deleteById(idRv);
    }

    public List<Rv> getRvByClient(Long idClient) {
        return rvRepository.getRvByClient(idClient);
    }

    public Rv getOne(Long idRv){
        return rvRepository.getById(idRv);
    }

    public List<Rv> getRvByClientName(String name) {
        return rvRepository.getRvByClientName(name);
    }
}
