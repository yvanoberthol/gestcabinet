package com.yvanscoop.gestcabinet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "specialite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartRv> cartRvs;


    public Specialite() {
        super();
    }

}
