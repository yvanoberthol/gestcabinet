package com.yvanscoop.gestcabinet.repositories;


import com.yvanscoop.gestcabinet.entities.security.Responsabilite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsabiliteRepository extends JpaRepository<Responsabilite, Long> {
    Responsabilite findByName(String name);
}
