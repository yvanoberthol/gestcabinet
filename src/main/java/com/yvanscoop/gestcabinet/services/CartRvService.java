package com.yvanscoop.gestcabinet.services;


import com.yvanscoop.gestcabinet.entities.CartRv;
import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Specialite;
import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.repositories.CartRvRepository;
import com.yvanscoop.gestcabinet.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartRvService {

    @Autowired
    private CartRvRepository cartRvRepository;


    public boolean add(Date jour, Client client, Creneau creneau, Specialite specialite) throws ParseException {

        CartRv crv = new CartRv(client, creneau, specialite);
        jour = DateUtils.gererDate(jour);
        crv.setJour(jour);
        cartRvRepository.save(crv);
        return true;
    }

    public void delete(Long idRv) {
        cartRvRepository.deleteById(idRv);
    }

    public List<CartRv> getCartRvByClient(Long idClient) {
        return cartRvRepository.getCartRvByClient(idClient);
    }

    public CartRv getCartRvMedecinJourByCreneau(String matricule, Date jour,Long idclient,Long idcreneau) {
        return cartRvRepository.getCartRvMedecinJourByCreneau(matricule,DateUtils.gererDate(jour),idclient,idcreneau);
    }

    public CartRv getOne(Long idCRv) {
        return cartRvRepository.getCartRvById(idCRv);
    }

    public List<CartRv> getRvMedecinJourPris(String matricule, Date jourDate,Long idclient) {
        return cartRvRepository.getCartRvMedecinJour(matricule,DateUtils.gererDate(jourDate),idclient);
    }
}