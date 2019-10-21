package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.Medecin;
import com.yvanscoop.gestcabinet.entities.MedecinSpecialite;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.services.MedecinService;
import com.yvanscoop.gestcabinet.services.MedecinSpecialiteService;
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
public class SpecialiteController {

    private static int currentPage = 1;
    private static int pageSize = 20;

    private static String motsearch = "";

    private static int currentPageDetail = 1;
    private static int pageSizeDetail = 20;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private MedecinSpecialiteService msService;

    @Autowired
    private MedecinService medecinService;

    @RequestMapping(value = "/specialites")
    public String getAllSpecialites(Model model,
                                    @RequestParam("nom") Optional<String> motcle,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        motcle.ifPresent(nom -> motsearch = nom);
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        Page<Specialite> specialitePage = specialiteService.findPaginated(motsearch, PageRequest.of(currentPage - 1, pageSize));

        int totalPages = specialitePage.getTotalPages();
        if (currentPage > totalPages) {
            currentPage = 1;
            specialitePage = specialiteService.findPaginated(motsearch, PageRequest.of(currentPage - 1, pageSize));
        }
        model.addAttribute("specialites", specialitePage);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "specialites";
    }

    @RequestMapping(value = "/medecins")
    public String medecins(@RequestParam(value = "domaine", defaultValue = "dermatologie") String nom, Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   Pageable pageable
    ) {
        page.ifPresent(p -> currentPageDetail = p);
        size.ifPresent(s -> pageSizeDetail = s);
        pageable = PageRequest.of(currentPageDetail - 1, pageSizeDetail);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Specialite specialite = specialiteService.getByNom(nom);
        if (specialite == null) {

        }
        List<MedecinSpecialite> lms = msService.findBySpecialite(specialite);
        List<Medecin> medecins = new ArrayList<>();

        lms.forEach(lm->{
            medecins.add(lm.getMedecin());
        });

        List<Medecin> list;
        if (medecins.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSizeDetail, medecins.size());
            list = medecins.subList(startItem, toIndex);
        }
        Page<Medecin> medecinPage = new PageImpl<>(list, pageable, medecins.size());
        int totalPages = medecinPage.getTotalPages();
        if (currentPageDetail > totalPages) {
            currentPageDetail = 1;
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
        return "medecins";
    }
}
