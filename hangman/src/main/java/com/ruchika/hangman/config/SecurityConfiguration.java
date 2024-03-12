package com.ruchika.hangman.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ruchika.hangman.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(
                (csrf) -> csrf.disable());

        http.sessionManagement(
                (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/login")
                        .permitAll());

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/register")
                        .permitAll());       

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.GET, "/game")
                        .hasAuthority("USER"));
        
        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.GET, "/games")
                        .hasAuthority("USER"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.GET, "/game/{gameId}")
                        .hasAuthority("USER"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/game/{gameId}/guess")
                        .hasAuthority("USER"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/game/{gameId}/quit")
                        .hasAuthority("USER"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.GET, "/words")
                        .hasAuthority("ADMIN"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/word")
                        .hasAuthority("ADMIN"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.requestMatchers(HttpMethod.POST, "/word/{wordId}")
                        .hasAuthority("ADMIN"));

        http.authorizeHttpRequests(
                (httpRequests) -> httpRequests.anyRequest()
                        .authenticated());

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
