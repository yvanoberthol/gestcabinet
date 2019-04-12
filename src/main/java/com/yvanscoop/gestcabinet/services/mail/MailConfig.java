package com.yvanscoop.gestcabinet.services.mail;


import com.yvanscoop.gestcabinet.entities.security.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("sendMailer")
public class MailConfig {

    @Autowired
    JavaMailSender javaMailSender;

    /*@Autowired
    private TemplateEngine templateEngine;*/

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /*public void sendMail(String from, String to, String subject, String body) throws Exception{

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        logger.info("Sending...");

        javaMailSender.send(mail);

        logger.info("Done!");
    }*/

    public void constructorResetTokenEmail(
            String contextPath, Locale locale, String token, Client client, String password) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        String url = contextPath + "/newAccount?token=" + token;
        String message = "\nPlease click on this link to verify your email and edit your personnal information";
        String password1 = "\nYour password is: " + password;
        String local = "\n" + locale.getCountry() + " " + locale.getVariant();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(client.getEmail());
        email.setSubject("Rv - new User");
        email.setText(url + message + password1 + local);
        email.setFrom("yvanoberthol@gmail.com");

        logger.info("Sending...");
        javaMailSender.send(email);
        logger.info("Done!");
    }


    /*public void constructOrderConfirmationEmail(User user, Order order, Locale france) {

        Context context =new Context();
        context.setVariable("order",order);
        context.setVariable("user",user);
        context.setVariable("cartItemList",order.getCartItemList());

        String text = templateEngine.process("orderConfirmationEmailTemplate",context);
        MimeMessagePreparator email = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("BookStore - order confirmation - "+order.getIdOrder());
                email.setText(text,true);
                email.setFrom(new InternetAddress("yvanoberthol@gmail.com"));

            }
        };

        javaMailSender.send(email);
    }*/
}
