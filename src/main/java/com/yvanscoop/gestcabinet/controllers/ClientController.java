package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.Rv;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ClientController {

    private static int currentPage= 1;
    private static int pageSize = 6;

    @Autowired
    MedecinService medecinService;


    @Autowired
    private ClientService clientService;


    @Autowired
    CartRvService crvService;

    @Autowired
    RvService rvService;


    @Autowired
    private SpecialiteService specialiteService;

    @RequestMapping(value = "/myProfile")
    public String medecinRv(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            Pageable pageable,
                            Principal principal
    ) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);
        pageable = PageRequest.of(currentPage - 1, pageSize);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Rv> listRvByclient = rvService.getRvByClientName(principal.getName());
        List<Rv> list;
        if (listRvByclient.size() < startItem)
            list = Collections.emptyList();
        else{
            int toIndex = Math.min(startItem + pageSize, listRvByclient.size());
            list = listRvByclient.subList(startItem, toIndex);
        }

        Page<Rv> rvPage = new PageImpl<>(list, pageable, listRvByclient.size());
        int totalPages = rvPage.getTotalPages();
        if (currentPage > totalPages) {
            currentPage = 1;
            rvPage = new PageImpl<>(list, pageable, listRvByclient.size());
        }
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        Client client = clientService.findByClientname(principal.getName());
        model.addAttribute("client", client);

        model.addAttribute("specialites", specialiteService.getAll(""));
        int nbreRvs = medecinService.getAll("").size();
        model.addAttribute("rvs", rvPage);
        model.addAttribute("rvNumber", nbreRvs);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

}
