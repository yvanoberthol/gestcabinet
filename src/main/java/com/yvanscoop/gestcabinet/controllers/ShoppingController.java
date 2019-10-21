package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.CartRv;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.services.CartRvService;
import com.yvanscoop.gestcabinet.services.RvService;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import com.yvanscoop.gestcabinet.services.mail.MailConfig;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    private RvService rvService;

    @Autowired
    private ClientService clientService;

    @Autowired
    CartRvService crvService;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private MailConfig mailConfig;

    @RequestMapping(value = "/myRvFromCart", method = RequestMethod.GET)
    public String getCRvByClient(Principal principal, Model model){
        Client client = clientService.findByClientname(principal.getName());
        List<CartRv> crvList = crvService.getCartRvByClient(client.getId());
        List<Specialite> specialites = specialiteService.getAll("");
        model.addAttribute("rvs",crvList);
        model.addAttribute("specialites",specialites);
        return "shoppingCart";
    }

    @RequestMapping(value = "/removeRvFromCart", method = RequestMethod.POST)
    public String deleteRv(@RequestParam(name = "id") Long idCRv){

        if (idCRv != null)
            crvService.delete(idCRv);

        // delete no content
        return "redirect:/myRvFromCart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    @Transactional
    public String checkout(Principal principal, RedirectAttributes redirectAttributes){
        Client client = clientService.findByClientname(principal.getName());

        //liste des rendez-vous effectués par le client
        List<CartRv> listRv = crvService.getCartRvByClient(client.getId());

        //envoie du mail de confirmation des réservations soumises
        mailConfig.constructOrderConfirmationEmail(client,listRv);

        //transfert des rendez-vous de la carte vers les rendez-vous pris
        listRv.forEach(cartRv -> {
            try {
                rvService.add(cartRv.getJour(),cartRv.getClient(),cartRv.getCreneau(),cartRv.getSpecialite());
                crvService.delete(cartRv.getId());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        redirectAttributes.addFlashAttribute("rvadd",true);

        return "redirect:/myRvFromCart";
    }


    @RequestMapping(value = "/checkout")
    public String checkout(Model model, Principal principal){
        Client client = clientService.findByClientname(principal.getName());

        List<CartRv> cartRvList = crvService.getCartRvByClient(client.getId());

        if (cartRvList.size() == 0){
            return "forward:/myRvFromCart";
        }
        model.addAttribute("specialites",specialiteService.getAll(""));
        model.addAttribute("classActiveInfo",true);
        model.addAttribute("rvs",cartRvList);
        model.addAttribute("client",client);

        return "checkout";
    }
}
