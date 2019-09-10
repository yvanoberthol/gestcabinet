package com.yvanscoop.gestcabinet.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
public class TokenRv {

    private static final int EXPIRATION = (int) (60 * 0.5);
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private Date expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rv_id", referencedColumnName = "id", nullable = false)
    private Rv rv;

    public TokenRv() {
    }

    public TokenRv(final String token, final Rv rv)
    {
        this.token = token;
        this.rv = rv;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, TokenRv.EXPIRATION);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(final String token){
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }
}
