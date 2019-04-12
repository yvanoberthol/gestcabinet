package com.yvanscoop.gestcabinet.repositories;


import com.yvanscoop.gestcabinet.entities.CompteMedecin;
import com.yvanscoop.gestcabinet.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteMedecinRepository extends JpaRepository<CompteMedecin,Long> {

    CompteMedecin getCompteMedecinByMedecin(Medecin medecin);
    boolean existsCompteMedecinByLogin(String login);
}
