package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.domains.AgendaMedecinJour;
import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.MedecinSpecialite;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.*;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MedecinSpecialiteService msService;

    @RequestMapping(value = "/medecin/detail/{id}")
    public String getMedecin(Model model, @PathVariable("id") Long id) {

        Medecin medecin = medecinService.getOne(id);
        List<MedecinSpecialite> lms = msService.findByMedecin(medecin);
        List<Specialite> specialiteParticulieres = new ArrayList<>();
        lms.forEach(lm->{
            specialiteParticulieres.add(lm.getSpecialite());
        });
        model.addAttribute("medecin", medecin);
        model.addAttribute("specialiteParticulieres", specialiteParticulieres);
        model.addAttribute("specialites", specialiteService.getAll(""));
        return "detailMedecin";
    }


    @RequestMapping(value = "/medecin/scheduler",method = {RequestMethod.POST,RequestMethod.GET})
    public String getMedecinScheduler(@RequestParam Map<String, String> form, Model model, Principal principal) {

        Client client = clientService.findByClientname(principal.getName());

        Long id = Long.parseLong(form.get("id"));

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
}
