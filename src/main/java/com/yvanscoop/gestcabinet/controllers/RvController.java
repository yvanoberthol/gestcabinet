package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.MedecinSpecialite;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.services.MedecinService;
import com.yvanscoop.gestcabinet.services.MedecinSpecialiteService;
import com.yvanscoop.gestcabinet.services.RvService;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RvController {

    private static int currentPage= 1;
    private static int pageSize = 6;

    @Autowired
    MedecinService medecinService;

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

}
