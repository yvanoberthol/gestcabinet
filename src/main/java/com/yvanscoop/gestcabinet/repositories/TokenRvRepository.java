package com.yvanscoop.gestcabinet.repositories;

import com.yvanscoop.gestcabinet.entities.Rv;
import com.yvanscoop.gestcabinet.entities.TokenRv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface TokenRvRepository extends JpaRepository<TokenRv,Long> {
    TokenRv findByToken(String token);
    TokenRv findByRv(Rv rv);

    @Modifying
    @Query("delete from TokenRv t where t.expiryDate <=?1")
    void deleteAllExpiredSince(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date now);
}
