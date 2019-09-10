package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.*;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.*;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CartRvController {

    private static int currentPage= 1;
    private static int pageSize = 6;

    @Autowired
    MedecinService medecinService;


    @Autowired
    private ClientService clientService;

    @Autowired
    private CreneauService creneauService;

    @Autowired
    RvService rvService;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private MedecinSpecialiteService msService;

    @RequestMapping(value = "/medecinRv")
    public String medecinRv(@RequestParam(value = "domaine", defaultValue = "dermatologie") String nom, Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   Pageable pageable
    ) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        pageable = PageRequest.of(currentPage - 1, pageSize);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Specialite specialite = specialiteService.getByNom(nom);

        List<MedecinSpecialite> lms = msService.findBySpecialite(specialite);
        List<Medecin> medecins = new ArrayList<>();

        lms.forEach(ms->{
            medecins.add(ms.getMedecin());
        });

        List<Medecin> list;
        if (medecins.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, medecins.size());
            list = medecins.subList(startItem, toIndex);
        }

        Page<Medecin> medecinPage = new PageImpl<>(list, pageable, medecins.size());
        int totalPages = medecinPage.getTotalPages();
        if (currentPage > totalPages) {
            currentPage = 1;
            medecinPage = new PageImpl<>(list, pageable, medecins.size());
        }
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<Specialite> specialiteList = specialiteService.getAll("");
        int nbreMedecins = medecinService.getAll("").size();
        model.addAttribute("specialites", specialiteList);
        model.addAttribute("medecins", medecinPage);
        model.addAttribute("medecinNumber", nbreMedecins);
        model.addAttribute("domaine", nom);
        return "medecinRv";
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
        List<Rv> reservations = rvService.getRvMedecinJourPris(creneau.getMedecin().getMatricule(), jourDate);


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
