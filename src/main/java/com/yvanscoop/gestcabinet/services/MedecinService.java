package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.domains.AgendaMedecinJour;
import com.yvanscoop.gestcabinet.domains.CreneauMedecinJour;
import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.Rv;
import com.yvanscoop.gestcabinet.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private CreneauService creneauService;

    @Autowired
    private RvService rvService;

    public List<Medecin> getAll(String mot) {
        return medecinRepository.findByMedecin(mot + "%");
    }

    public Page<Medecin> findPaginated(String mot, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Medecin> list;

        if (getAll(mot).size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, getAll(mot).size());
            list = getAll(mot).subList(startItem, toIndex);
        }

        return new PageImpl<Medecin>(list, PageRequest.of(currentPage, pageSize), getAll(mot).size());
    }

    public Medecin getOne(Long id) {
        return medecinRepository.getOne(id);
    }


    public Medecin getByNom(String nom) {
        return medecinRepository.getByMedecinname(nom);
    }

    public Medecin getByMatricule(String matricule) {
        // TODO Auto-generated method stub
        return medecinRepository.findByMatricule(matricule);
    }


    public AgendaMedecinJour getAgendaMedecinJour(String matricule, Date jour) {

        // liste des créneaux horaires du médecin
        List<Creneau> creneauxHoraires = creneauService.getAllByMedecin(matricule);

        jour = gererDate(jour);

        // liste des réservations de ce même médecin pour ce même jour
        List<Rv> reservations = rvService.getRvMedecinJour(matricule, jour);

        // on crée un dictionnaire à partir des Rv pris
        Map<Long, Rv> hReservations = new Hashtable<Long, Rv>();
        for (Rv resa : reservations) {
            hReservations.put(resa.getCreneau().getId(), resa);
        }
        // on crée l'agenda pour le jour demandé
        AgendaMedecinJour agenda = new AgendaMedecinJour();
        // le médecin
        agenda.setMedecin(medecinRepository.findByMatricule(matricule));
        // le jour
        agenda.setJour(jour);
        // les créneaux de réservation
        CreneauMedecinJour[] creneauxMedecinJour = new CreneauMedecinJour[creneauxHoraires.size()];


        // remplissage des créneaux de réservation
        for (int i = 0; i < creneauxHoraires.size(); i++) {

            // ligne i agenda
            creneauxMedecinJour[i] = new CreneauMedecinJour();

            // créneau horaire
            Creneau creneau = creneauxHoraires.get(i);
            long idCreneau = creneau.getId();
            creneauxMedecinJour[i].setCreneau(creneau);

            // le créneau est-il libre ou réservé ?
            if (hReservations.containsKey(idCreneau)) {

                // le créneau est occupé - on note la résa
                Rv resa = hReservations.get(idCreneau);
                creneauxMedecinJour[i].setRv(resa);
            }
        }

        agenda.setCreneauxMedecinJour(creneauxMedecinJour);

        // on rend le résultat
        return agenda;
    }

    public Date gererDate(Date jour){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        Calendar cal = Calendar.getInstance();
        cal.setTime(jour);
        cal.add(Calendar.DATE,1);
        return cal.getTime();
    }
}
