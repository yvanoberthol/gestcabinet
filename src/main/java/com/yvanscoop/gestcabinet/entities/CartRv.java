package com.yvanscoop.gestcabinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yvanscoop.gestcabinet.entities.security.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cartrv")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartRv implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jour;

    // un rv est lié à un client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    // un rv est lié à un créneau
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_creneau")
    private Creneau creneau;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_specialite")
    private Specialite specialite;


    // constructeur par défaut
    public CartRv() {

    }

    // avec paramètres
    public CartRv(Client client, Creneau creneau, Specialite specialite) {
        this.client = client;
        this.creneau = creneau;
        this.specialite = specialite;
    }

    // avec paramètres
    public CartRv(Client client, Date jour, Creneau creneau, Specialite specialite) {
        this.client = client;
        this.creneau = creneau;
        this.specialite = specialite;
        this.jour = jour;
    }

}