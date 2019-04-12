package com.yvanscoop.gestcabinet.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="compte_medecin")
@Data @NoArgsConstructor @AllArgsConstructor
public class CompteMedecin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(unique = true)
    private String login;

    private String password;

    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "medecin_id",nullable = false)
    private Medecin medecin;
}
