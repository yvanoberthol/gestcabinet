package com.yvanscoop.gestcabinet.services.security;


import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientSecurityService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(final String clientname) throws UsernameNotFoundException {
        final Client client = clientRepository.findByUsernameOrEmail(clientname,clientname);

        if (null == client) {
            throw new UsernameNotFoundException("clientname not found");
        }
        return client;
    }
}
