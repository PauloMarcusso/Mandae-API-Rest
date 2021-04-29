package com.api.mandae.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cozinhas/**").hasAnyAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.PUT, "/cozinhas/**").hasAnyAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.GET, "/cozinhas/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .cors().and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
//                .oauth2ResourceServer().opaqueToken();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    var authorities = jwt.getClaimAsStringList("authorities");

                    if(authorities == null){
                        authorities = Collections.emptyList();
                    }

                    return authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                });

        return jwtAuthenticationConverter;
    }

    /**
     * Configuração com Chave Secreta Simétrica
     */
//    @Bean
//    public JwtDecoder jwtDecoder(){
//
//        var secretKey = new SecretKeySpec("vASVsydvcyavsdavsydvaysvdyasvdTSVIysavdytav".getBytes(), "HmacSHA256");
//
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }

}
