package com.yvanscoop.gestcabinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
public class Specialite implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "le nom de la spécialité ne doit pas être vide.")
    @Length(min = 6, message = "le nom de la spécialité doit être au minimum 6 caractères.")
    private String nom;

    @Column(length = 3000)
    @Length(min = 14, message = "entrez une description pour ce domaine.")
    private String description;

    @OneToMany(mappedBy = "specialite", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<MedecinSpecialite> medecinSpecialites;

    @OneToMany(mappedBy = "specialite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rv> rvs;


    public Specialite() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desription) {
        this.description = desription;
    }


}
