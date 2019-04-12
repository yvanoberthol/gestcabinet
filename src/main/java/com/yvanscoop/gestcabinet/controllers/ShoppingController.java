package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.Rv;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.RvService;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    private RvService rvService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SpecialiteService specialiteService;

    @RequestMapping(value = "/myRv", method = RequestMethod.GET)
    public String getRvByClient(Principal principal, Model model){
        Client client = clientService.findByClientname(principal.getName());
        List<Rv> rvList = rvService.getRvByClient(client.getId());
        List<Specialite> specialites = specialiteService.getAll("");
        model.addAttribute("rvs",rvList);
        model.addAttribute("specialites",specialites);
        return "shoppingCart";
    }

    @RequestMapping(value = "/removeRv", method = RequestMethod.POST)
    public String deleteRv(@RequestParam(name = "id") Long idRv){

        if (idRv != null){
            rvService.delete(idRv);
        }

        // delete no content
        return "redirect:/myRv";
    }
}
