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
@Table(name = "rv")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rv implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jour;

    private boolean annule = false;

    // un rv est lié à un client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    // un rv est lié à un créneau
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_creneau")
    private Creneau creneau;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_specialite")
    private Specialite specialite;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rv")
    private TokenRv tokenRv;

    // constructeur par défaut
    public Rv() {

    }

    // avec paramètres
    public Rv(Client client, Creneau creneau,Specialite specialite) {
        this.client = client;
        this.creneau = creneau;
        this.specialite = specialite;
        this.annule = false;
    }

}