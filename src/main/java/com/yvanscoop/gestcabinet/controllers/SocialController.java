package com.yvanscoop.gestcabinet.controllers;
/*

import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.repositories.ClientRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//@Controller
public class SocialController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    @Autowired
    private ClientRepository clientRegistrationRepository;

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {
        Iterable<Client> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                Client.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<Client>) clientRegistrationRepository;
        }

        if (clientRegistrations != null) {
            clientRegistrations.forEach(registration ->
                    oauth2AuthenticationUrls.put(registration.getUsername(),
                            authorizationRequestBaseUri + "/" + registration.get())
            );
        }
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "oauth_login";
    }


    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            model.addAttribute("name", userAttributes.get("name"));
        }

        return "loginSuccess";
    }
}
*/
