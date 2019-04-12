package com.yvanscoop.gestcabinet.restcontrollers;


import com.yvanscoop.gestcabinet.domains.AgendaMedecinJour;
import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.CreneauService;
import com.yvanscoop.gestcabinet.services.MedecinService;
import com.yvanscoop.gestcabinet.services.RvService;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RvMedecinRestController {

    @Autowired
    private MedecinService medecinService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreneauService creneauService;

    @Autowired
    private RvService rvService;

    @Autowired
    private SpecialiteService specialiteService;


    @RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AgendaMedecinJour> getAgendaMedecinJour(@PathVariable("idMedecin") long idMedecin, @PathVariable("jour") String jour, HttpServletResponse response) {

        // on vérifie la date
        Date jourAgenda = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            jourAgenda = sdf.parse(jour);
        } catch (ParseException e) {
            System.out.println(String.format("jour [%s] invalide", jour));
        }

        // on récupère le médecin
        Medecin medecin = medecinService.getOne(idMedecin);
        if (medecin == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // on récupère son agenda
        AgendaMedecinJour agenda = null;
        try {
            agenda = medecinService.getAgendaMedecinJour(medecin.getMatricule(), jourAgenda);
        } catch (Exception e1) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // ok
        return new ResponseEntity<>(agenda,HttpStatus.OK);
    }


    @RequestMapping(value = "/takeRv", method = RequestMethod.POST)
    public ResponseEntity<?> saveRv(String jour,Long idClient,Long idCreneau,Long idSpecialite) throws ParseException {

        if (idClient != null){
            Client client = clientService.getClient(idClient);
            if (idCreneau !=null){
                Creneau creneau = creneauService.getCrenauById(idCreneau);
                if (idSpecialite != null){
                    Specialite specialite = specialiteService.getOne(idSpecialite);
                    if (jour != null){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setLenient(false);
                        Date jourDate = sdf.parse(jour);
                        rvService.add(jourDate,client,creneau,specialite);
                    }
                }

            }
        }

        // ok
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/removeRv", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRv(Long idRv){

        if (idRv != null){
            rvService.delete(idRv);
        }

        // delete no content
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
