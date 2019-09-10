package com.yvanscoop.gestcabinet.config;


import com.yvanscoop.gestcabinet.services.security.ClientSecurityService;
import com.yvanscoop.gestcabinet.utility.SecurityUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private ClientSecurityService clientSecurityService;

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/imgs/**",
            "/",
            "/home",
            "/newAccount",
            "/login",
            "/newClient",
            "/captcha",
            "/medecins",
            "/specialites",
            "/forgetPassword",
            "/api/getAgendaMedecinJour/**",
            "/medecin/detail/**",
            "/inscription",
            "/node_modules/**"
    };


    private BCryptPasswordEncoder passwordEncoder() {
        return SecurityUtility.passwordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("http");
        http
                .authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable().formLogin()
                .failureUrl("/login?error").defaultSuccessUrl("/").loginPage("/login").permitAll()
                /*.and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)*/
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").deleteCookies("remember-me").permitAll()
                .and().rememberMe();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(clientSecurityService).passwordEncoder(passwordEncoder());
    }
}
