package com.yvanscoop.gestcabinet.domains;


import com.yvanscoop.gestcabinet.entities.Medecin;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class AgendaMedecinJour implements Serializable {

    // champs
    private Medecin medecin;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jour;

    private CreneauMedecinJour[] creneauxMedecinJour;

    // constructeurs
    public AgendaMedecinJour() {

    }

    public AgendaMedecinJour(Medecin medecin, Date jour, CreneauMedecinJour[] creneauxMedecinJour) {
        this.medecin = medecin;
        this.jour = jour;
        this.creneauxMedecinJour = creneauxMedecinJour;
    }


    // getters et setters

    public CreneauMedecinJour[] getCreneauxMedecinJour() {
        return creneauxMedecinJour;
    }

    public void setCreneauxMedecinJour(CreneauMedecinJour[] creneauxMedecinJour) {
        this.creneauxMedecinJour = creneauxMedecinJour;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

}
