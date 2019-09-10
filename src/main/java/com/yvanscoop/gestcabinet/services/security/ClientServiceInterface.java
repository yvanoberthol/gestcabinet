package com.yvanscoop.gestcabinet.services.security;


import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.entities.security.ClientResponsabilite;
import com.yvanscoop.gestcabinet.entities.security.PasswordResetToken;

import java.util.Set;

public interface ClientServiceInterface {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForClient(final Client client, final String token);

    Client findByClientname(String clientname);

    Client findByEmail(String email);

    Client createClient(Client client, Set<ClientResponsabilite> clientResponsabilites) throws Exception;

    Client saveClient(Client client);

    PasswordResetToken saveToken(PasswordResetToken passwordResetToken);

    PasswordResetToken findTokenEmail(String email);

    Client findByPhone(String phone);
}
