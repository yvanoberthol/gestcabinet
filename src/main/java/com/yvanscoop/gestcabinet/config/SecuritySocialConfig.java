package com.yvanscoop.gestcabinet.config;

/*@Configuration
    @EnableWebSecurity
    @PropertySource("classpath:application.properties")
    extends WebSecurityConfigurerAdapter
*/
/*public class SecuritySocialConfig{

    private static List<String> clients = Arrays.asList("google", "facebook");
    private static String CLIENT_PROPERTY_KEY
            = "spring.security.oauth2.client.registration.";

    @Autowired
    private Environment env;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-id");

        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("facebook")) {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService());

        http.authorizeRequests()
                .antMatchers("/oauth_login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/oauth_login")
                .and()
                .oauth2Login()
                    .defaultSuccessUrl("/loginSuccess")
                    .failureUrl("/loginFailure")
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize-client")
                    .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                    .tokenEndpoint()
                    .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                    .redirectionEndpoint()
                    .baseUri("/oauth2/redirect");
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {

        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
    accessTokenResponseClient() {

        return new NimbusAuthorizationCodeTokenResponseClient();
    }
}*/
