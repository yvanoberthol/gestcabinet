package com.yvanscoop.gestcabinet.restcontrollers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RestUserController {
    @GetMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Principal user(Principal principal){
        return principal;
    }
}
