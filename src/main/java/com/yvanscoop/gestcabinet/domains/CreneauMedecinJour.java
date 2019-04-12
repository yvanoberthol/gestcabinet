package com.yvanscoop.gestcabinet.domains;


import com.yvanscoop.gestcabinet.entities.Creneau;
import com.yvanscoop.gestcabinet.entities.Rv;

import java.io.Serializable;

public class CreneauMedecinJour implements Serializable {

    // champs
    private Creneau creneau;
    private Rv rv;

    // constructeurs
    public CreneauMedecinJour() {

    }

    public CreneauMedecinJour(Creneau creneau, Rv rv) {
        this.creneau = creneau;
        this.rv = rv;
    }

    // getters et setters

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public Rv getRv() {
        return rv;
    }

    public void setRv(Rv rv) {
        this.rv = rv;
    }

}
