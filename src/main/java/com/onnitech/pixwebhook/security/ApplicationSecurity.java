package com.onnitech.pixwebhook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class ApplicationSecurity {

    private ApplicationSecurity() {
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                // Rotas liberadas
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", "/authorization/**").permitAll()

                // Rota que exige mTLS
                .requestMatchers("/callback/**").access((auth, ctx) -> {
                    var certs = (java.security.cert.X509Certificate[])
                            ctx.getRequest().getAttribute("javax.servlet.request.X509Certificate");
                    var hasCert = certs != null && certs.length > 0;
                    return new AuthorizationDecision(hasCert);
                })

                // Outras rotas liberadas
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(
                Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(Boolean.TRUE);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
