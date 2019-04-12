package com.yvanscoop.gestcabinet.domains;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class FormDateScheduler {
    private Long idCreneau;
    private String specialiteName;
    private String clientName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date jour;

    public FormDateScheduler() {
    }

    public Long getIdCreneau() {
        return idCreneau;
    }

    public void setIdCreneau(Long idCreneau) {
        this.idCreneau = idCreneau;
    }

    public String getSpecialiteName() {
        return specialiteName;
    }

    public void setSpecialiteName(String specialiteName) {
        this.specialiteName = specialiteName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }
}
