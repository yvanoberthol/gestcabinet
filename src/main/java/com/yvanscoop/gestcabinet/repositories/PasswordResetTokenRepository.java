package com.yvanscoop.gestcabinet.repositories;


import com.yvanscoop.gestcabinet.entities.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    @Query("select p from PasswordResetToken p where p.client in (select c from Client c where c.email = :email)")
    PasswordResetToken findTokenByClientEmail(@Param("email") String email);

    @Modifying
    @Query("delete from PasswordResetToken p where p.expiryDate <=?1")
    void deleteAllExpiredSince(Date now);
}
