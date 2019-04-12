package com.yvanscoop.gestcabinet.controllers;


import com.yvanscoop.gestcabinet.entities.security.Client;
import com.yvanscoop.gestcabinet.entities.security.ClientResponsabilite;
import com.yvanscoop.gestcabinet.entities.security.PasswordResetToken;
import com.yvanscoop.gestcabinet.entities.security.Responsabilite;
import com.yvanscoop.gestcabinet.services.SpecialiteService;
import com.yvanscoop.gestcabinet.services.mail.MailConfig;
import com.yvanscoop.gestcabinet.services.security.ClientSecurityService;
import com.yvanscoop.gestcabinet.services.security.ClientService;
import com.yvanscoop.gestcabinet.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private ClientSecurityService clientSecurityService;

    @Autowired
    @Qualifier("sendMailer")
    private MailConfig mailConfig;


    /******************************/
    /* All Actions of PAGE INDEX or HOME */

    /******************************/

    @RequestMapping(value = {"/home", "/"})
    public String home(/*HttpSession session,*/ Model model) {
        //session.setAttribute("date1", new Date());
        model.addAttribute("specialites", specialiteService.getAll(""));
        return "home";
    }


    /******************************/
    /* All Actions of USER ACCOUNT */

    /******************************/


    /*Login page ou page de connexion*/
    @RequestMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("specialites", specialiteService.getAll(""));
        return "myAccount";
    }

    public void login(HttpServletRequest req, String user, String pass) {
    }

    @RequestMapping(value = "/forgetPassword")
    public String forget(Model model) {
        model.addAttribute("specialites", specialiteService.getAll(""));
        return "passwordReset";
    }

    @RequestMapping(value = "/inscription")
    public String inscription(Model model) {
        model.addAttribute("specialites", specialiteService.getAll(""));
        return "inscription";
    }



    /*forgot password or mot de passe oublié*/
    @Transactional
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public String forgetPassword(Model model,
                                 HttpServletRequest request,
                                 @ModelAttribute("email") String userEmail) {

        Client client = clientService.findByEmail(userEmail);
        if (client == null) {
            model.addAttribute("emailNotExists", true);
            return "passwordReset";
        }

        String password = SecurityUtility.randomPassword();
//        System.out.println("The password is "+password);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        client.setPassword(encryptedPassword);


        clientService.saveClient(client);

        PasswordResetToken passwordResetToken = clientService.findTokenEmail(userEmail);
        String token = UUID.randomUUID().toString();
        passwordResetToken.setToken(token);


        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        mailConfig.constructorResetTokenEmail(appUrl, request.getLocale(), token, client, password);

        // save a token
        clientService.saveToken(passwordResetToken);

        model.addAttribute("specialites", specialiteService.getAll(""));
        model.addAttribute("forgetPasswordEmailSent", true);
        return "passwordReset";
    }

    /*new user or creation d'un nouvel utilisateur*/
    @Transactional
    @RequestMapping(value = "/newClient", method = RequestMethod.POST)
    public String addClient(HttpServletRequest request,
                            @ModelAttribute("email") String userEmail,
                            @ModelAttribute("username") String username,
                            Model model) throws Exception {
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);
        model.addAttribute("specialites", specialiteService.getAll(""));

        if (clientService.findByClientname(username) != null) {
            model.addAttribute("specialites", specialiteService.getAll(""));
            return "inscription";
        }

        if (clientService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);
            return "inscription";
        }

        Client client = new Client();
        client.setUsername(username);
        client.setEmail(userEmail);
        String password = SecurityUtility.randomPassword();
//        System.out.println("The password is "+password);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        client.setPassword(encryptedPassword);

        Responsabilite responsabilite = new Responsabilite();
        responsabilite.setName("ROLE_USER");

        Set<ClientResponsabilite> clientResponsabilites = new HashSet<>();
        clientResponsabilites.add(new ClientResponsabilite(client, responsabilite));

        String token = UUID.randomUUID().toString();

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        mailConfig.constructorResetTokenEmail(appUrl, request.getLocale(), token, client, password);

        clientService.createClient(client, clientResponsabilites);

        clientService.createPasswordResetTokenForClient(client, token);

        model.addAttribute("emailSent", true);
        return "inscription";
    }


    /*new account or creation d'un nouveau compte*/
    @RequestMapping(value = "/newAccount")
    public String newAccount(Model model,
                             @RequestParam("token") String token) {

//        System.out.println("The token is: "+token);
        PasswordResetToken passwordResetToken = clientService.getPasswordResetToken(token);
//        System.out.println("The passwordResetToken is "+passwordResetToken);
        if (passwordResetToken == null) {
            String message = "Invalid Token";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        Client client = passwordResetToken.getClient();
        String username = client.getUsername();
//        System.out.println("the username is "+username);


        // charger l'utilisateur authentifié et accepté son authentification
        UserDetails userDetails = clientSecurityService.loadUserByUsername(username);
//        System.out.println("the userDetails is "+userDetails);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("specialites", specialiteService.getAll(""));
        model.addAttribute("client", client);
        return "myProfile";
    }

    // my profile ou modification et consultation de votre profil
    @RequestMapping(value = "/myProfile")
    public String myProfile(Model model, Principal principal) {

        Client client = clientService.findByClientname(principal.getName());
        model.addAttribute("client", client);

        model.addAttribute("specialites", specialiteService.getAll(""));
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    //update usr info or modification des infos de l'utilisateur
    @RequestMapping(value = "/updateClientInfo", method = RequestMethod.POST)
    public String updateUserInfo(@ModelAttribute("user") Client client,
                                 @ModelAttribute("newPassword") String newPassword,
                                 Model model, RedirectAttributes redirectAttribute) throws Exception {

        Client currentClient = clientService.getClient(client.getId());
        if (currentClient == null) {
            throw new Exception("client not found");
        }

        //on verifie que le mot de passe courant n'est pas vide
        if (client.getPassword().isEmpty()) {
            redirectAttribute.addFlashAttribute("emptyPassword", true);
            return "redirect:/myProfile";
        }

        //on verifie que le mot de passe courant est valide
        if (client.getPassword() != null) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentClient.getPassword();
            if (!passwordEncoder.matches(client.getPassword(), dbPassword)) {
                redirectAttribute.addFlashAttribute("incorrectPassword", true);
                return "redirect:/myProfile";
            }
        }

        /*check email already exists*/
        if (clientService.findByEmail(client.getEmail()) != null) {
            if (!clientService.findByEmail(client.getEmail()).getId().equals(currentClient.getId())) {
                redirectAttribute.addFlashAttribute("emailExists", true);
                return "redirect:/myProfile";
            }
        }

        /*check username already exists*/
        if (clientService.findByClientname(client.getUsername()) != null) {
            if (!clientService.findByClientname(client.getUsername()).getId().equals(currentClient.getId())) {
                redirectAttribute.addFlashAttribute("usernameExists", true);
                return "redirect:/myProfile";
            }
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentClient.getPassword();
            if (passwordEncoder.matches(client.getPassword(), dbPassword)) {
                currentClient.setPassword(passwordEncoder.encode(newPassword));
            } else {
                redirectAttribute.addFlashAttribute("incorrectPassword", true);
                return "redirect:/myProfile";
            }
        }

        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setUsername(client.getUsername());
        currentClient.setEmail(client.getEmail());
        currentClient.setPhone(client.getPhone());

        clientService.saveClient(currentClient);

        model.addAttribute("specialites", specialiteService.getAll(""));
        model.addAttribute("updateClientInfo", true);
        model.addAttribute("client", currentClient);
        model.addAttribute("classActiveEdit", true);


        UserDetails userDetails = clientSecurityService.loadUserByUsername(currentClient.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "myProfile";
    }


}
