package com.yvanscoop.gestcabinet.services.security;


import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.entities.security.ClientResponsabilite;
import com.yvanscoop.gestcabinet.entities.security.PasswordResetToken;
import com.yvanscoop.gestcabinet.repositories.ClientRepository;
import com.yvanscoop.gestcabinet.repositories.PasswordResetTokenRepository;
import com.yvanscoop.gestcabinet.repositories.ResponsabiliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
@Transactional
public class ClientService implements ClientServiceInterface {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ResponsabiliteRepository responsabiliteRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForClient(Client client, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, client);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public Client findByClientname(String clientname) {
        return clientRepository.findByUsername(clientname);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client createClient(Client client, Set<ClientResponsabilite> clientResponsabilites) throws Exception {
        Client localClient = clientRepository.findByUsername(client.getUsername());

        if (localClient != null) {
            throw new Exception("client already exists. Nothing will be done");
        } else {
            for (ClientResponsabilite ur : clientResponsabilites) {
                responsabiliteRepository.save(ur.getResponsabilite());
            }
            client.getClientResponsabilites().addAll(clientResponsabilites);

            localClient = clientRepository.save(client);
        }
        return localClient;
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public PasswordResetToken saveToken(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findTokenEmail(String email) {
        return passwordResetTokenRepository.findTokenByClientEmail(email);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public Client getClient(Long id) {
        return clientRepository.getOne(id);
    }

    public void deleteTokenClient(Date date){
        passwordResetTokenRepository.deleteAllExpiredSince(new Date());
    }

}
