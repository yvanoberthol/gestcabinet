package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.CartRv;
import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.*;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ClientController {

    private static int currentPage= 1;
    private static int pageSize = 6;

    @Autowired
    MedecinService medecinService;


    @Autowired
    private ClientService clientService;

    @Autowired
    private CreneauService creneauService;

    @Autowired
    CartRvService crvService;

    @Autowired
    RvService rvService;


    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private MedecinSpecialiteService msService;

    //mettre un rv dans sa carte de rendez-vous
    @RequestMapping(value = "/takeRvFromCart", method = RequestMethod.POST)
    public String saveCRv(String jour, String  nameClient, Long idCreneau, String specialiteName,RedirectAttributes redirectAttribute) throws ParseException {

        Client client = null;
        Creneau creneau = null;
        Specialite specialite = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date jourDate = sdf.parse(jour);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jourDate);
        int numberDay =calendar.get(Calendar.DAY_OF_WEEK);


        if (nameClient != null) {
            client = clientService.findByClientname(nameClient);
        }
        if (idCreneau !=null) {
            creneau = creneauService.getCrenauById(idCreneau);
        }

        if (specialiteName != null){
            specialite = specialiteService.getByNom(specialiteName);
        }

        assert creneau != null;

        CartRv creservation = crvService.getCartRvMedecinJourByCreneau(creneau.getMedecin().getMatricule(), jourDate,client.getId(),creneau.getId());
        if (creservation != null ){
            redirectAttribute.addFlashAttribute("rvincart", true);
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        List<CartRv> creservations = crvService.getRvMedecinJourPris(creneau.getMedecin().getMatricule(), jourDate,client.getId());
        if (creservations.size() > 0){
            redirectAttribute.addFlashAttribute("notmuchrvmedecinincart", true);
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        if (jourDate.before(new Date()) || jourDate.equals(new Date())){
            redirectAttribute.addFlashAttribute("jourerror", true);
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        if (numberDay==1 || numberDay==7){
            redirectAttribute.addFlashAttribute("jourrepos", true);
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        if (jour != null){
            crvService.add(jourDate,client,creneau,specialite);
            redirectAttribute.addFlashAttribute("addToCart", true);
        }

        return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
    }

    @RequestMapping(value = "/deleteRvFromCart", method = RequestMethod.POST)
    public String deleteRv(Long idCRv, String jour, String  nameClient, Long idCreneau, String specialiteName,RedirectAttributes redirectAttributes) throws ParseException {

        Creneau creneau = null;
        Client client = clientService.findByClientname(nameClient);
        CartRv crv = crvService.getOne(idCRv);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date jourDate = sdf.parse(jour);

        if (idCreneau !=null) {
            creneau = creneauService.getCrenauById(idCreneau);
        }

        if (jourDate.before(new Date()) || jourDate.equals(new Date())){
            redirectAttributes.addFlashAttribute("jourerror", true);
            assert creneau != null;
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        if (idCRv != null && crv.getClient().equals(client)){
            crvService.delete(idCRv);
        }

        // ok
        assert creneau != null;
        return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;

    }

}
