package com.yvanscoop.gestcabinet.entities.security;

import org.springframework.security.core.GrantedAuthority;

public class Authotity implements GrantedAuthority {
    private final String authority;

    public Authotity(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
