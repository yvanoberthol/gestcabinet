package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.domains.AgendaMedecinJour;
import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.Rv;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.CreneauService;
import com.yvanscoop.gestcabinet.services.MedecinService;
import com.yvanscoop.gestcabinet.services.RvService;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MedecinController {
    private static int currentPage = 1;
    private static int pageSize = 5;
    private static String motsearch = "";

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

    @RequestMapping(value = "/medecin/{id}")
    public String getMedecin(Model model, @PathVariable("id") Long id) {

        Medecin medecin = medecinService.getOne(id);
        if (medecin == null) {
            model.addAttribute("userNotExists", true);
        } else {
            model.addAttribute("specialites", specialiteService.getAll(""));
            model.addAttribute("medecin", medecin);
        }

        return "medecin";
    }

    @RequestMapping(value = "/medecin/scheduler",method = {RequestMethod.POST,RequestMethod.GET})
    public String getMedecinScheduler(@RequestParam Map<String, String> form, Model model, Principal principal) {

        Client client = clientService.findByClientname(principal.getName());

        Long id = Long.parseLong(form.get("id"));

        System.out.print("ID: "+form.get("id"));
        Specialite specialite = specialiteService.getByNom(form.get("specialite"));
        Medecin medecin = medecinService.getOne(id);


        if(specialite == null){
            model.addAttribute("specialiteNotExists", true);
            return "redirect:/medecinRv";
        }

        if (medecin == null) {
            model.addAttribute("medecinNotExists", true);
            return "redirect:/medecinRv";
        }


        // on vérifie la date
        Date jourAgenda = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            jourAgenda = sdf.parse(form.get("jour"));
        } catch (ParseException e) {
            System.out.println(String.format("jour [%s] invalide", form.get("jour")));
        }


        // on récupère son agenda
        AgendaMedecinJour agenda = null;
        try {
            agenda = medecinService.getAgendaMedecinJour(medecin.getMatricule(), jourAgenda);
            model.addAttribute("agenda",agenda);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        model.addAttribute("specialites", specialiteService.getAll(""));
        model.addAttribute("client",client);
        model.addAttribute("medecin",medecin);
        model.addAttribute("specialite",specialite);
        model.addAttribute("jour",form.get("jour"));
        assert jourAgenda != null;
        model.addAttribute("jourDate",jourAgenda);

        return "organisationMedecin";
    }

    @RequestMapping(value = "/takeRv", method = RequestMethod.POST)
    public String saveRv(String jour, String  nameClient, Long idCreneau, String specialiteName,Model model,RedirectAttributes redirectAttribute) throws ParseException {

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
        List<Rv> reservations = rvService.getRvMedecinJour(creneau.getMedecin().getMatricule(), jourDate);

        if (reservations.size() > 0){
            redirectAttribute.addFlashAttribute("rvnotsameday", true);
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
            rvService.add(jourDate,client,creneau,specialite);
        }

        return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
    }

    @RequestMapping(value = "/deleteRv", method = RequestMethod.POST)
    public String deleteRv(Long idRv, String jour, String  nameClient, Long idCreneau, String specialiteName,RedirectAttributes redirectAttributes) throws ParseException {

        Creneau creneau = null;
        Client client = clientService.findByClientname(nameClient);
        Rv rv = rvService.getOne(idRv);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date jourDate = sdf.parse(jour);

        if (idCreneau !=null) {
            creneau = creneauService.getCrenauById(idCreneau);
        }

        if (idRv != null && rv.getClient().equals(client)){
            rvService.delete(idRv);
        }

        if (jourDate.before(new Date()) || jourDate.equals(new Date())){
            redirectAttributes.addFlashAttribute("jourerror", true);
            assert creneau != null;
            return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;
        }

        // ok
        assert creneau != null;
        return "redirect:/medecin/scheduler?id="+creneau.getMedecin().getId()+"&specialite="+specialiteName+"&jour="+jour;

    }

}
