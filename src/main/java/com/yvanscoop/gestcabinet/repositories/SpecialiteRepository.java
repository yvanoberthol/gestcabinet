package com.yvanscoop.gestcabinet.repositories;


import com.yvanscoop.gestcabinet.entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    @Query("select s from Specialite s where s.nom = :nom")
    Specialite getSpecialiteByNom(@Param("nom") String nom);

    @Query("select s from Specialite s where s.nom like :nom")
    List<Specialite> findByNom(@Param("nom") String nom);

}
