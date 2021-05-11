package com.api.mandae.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);

        /*Segurança para quando há poucos clients - HARD CODED*/
//                .inMemory()
//                    .withClient("mandae-web")
//                    .secret(passwordEncoder.encode("123"))
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .scopes("WRITE", "READ")
//                    .accessTokenValiditySeconds(60 * 60 * 6)// 6 horas (padrão é 12 horas);
//
//                //Authorization Code Grant Type
//                .and()
//                    .withClient("analytics")
//                    .secret(passwordEncoder.encode("123"))
//                    .authorizedGrantTypes("authorization_code")
//                    .redirectUris("http://localhost:8082")
//                    .scopes("WRITE", "READ")
//
//                //Client Credentials Flow
//                .and()
//                    .withClient("faturamento")
//                    .secret(passwordEncoder.encode("123"))
//                    .authorizedGrantTypes("client_credentials")
//                    .scopes("WRITE", "READ")
//
//                .and()
//                    .withClient("checktoken")
//                    .secret(passwordEncoder.encode("check123"))
//
//                //Implict Grant Type
//                .and()
//                    .withClient("implicit")
//                    .authorizedGrantTypes("implicit")
//                    .scopes("WRITE", "READ")
//                    .redirectUris("http://aplicacao-cliente");
    }

    //Para o PasswordFlow, precisamos desse método
    @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
//                .tokenStore(redisTokenStore())
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey("vASVsydvcyavsdavsydvaysvdyasvdTSVIysavdytav");//precisa ter 256bits;

        var keyStorePass = jwtKeyStoreProperties.getPassword();
        var keyPairAlias = jwtKeyStoreProperties.getKeyPairAlias();

        var keyStoreKeyFactory = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    @Override public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.checkTokenAccess("isAuthenticated()")
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
        .allowFormAuthenticationForClients();
        //security.checkTokenAccess("permitAll()");
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

//    private TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
