package com.yvanscoop.gestcabinet.repositories;


import com.yvanscoop.gestcabinet.entities.security.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsernameOrEmail(String username, String email);
    Client findByUsername(String username);

    Client findByEmail(String email);

    Client findByPhone(String phone);
}
