package com.example.EvaluationEnLigne.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired
	private  JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	  private  AuthenticationProvider authenticationProvider;
	@Autowired
	  private  Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
	@Autowired
	  private  CustomAccessDeniedHandler accessDeniedHandler;
	  
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf(AbstractHttpConfigurer::disable)
	                .exceptionHandling(exception -> exception
	                        .authenticationEntryPoint(unauthorizedEntryPoint)
	                        .accessDeniedHandler(accessDeniedHandler))
	                .authorizeHttpRequests(request ->
	                        request
	                                .requestMatchers(
	                                		"/**",
	                                		"/Admin/**",
	                                		"/Evaluation/**",
	                                		"/Matiere/**",
	                                		"/Epreuve/**",
	                                		"/Etudiant/**",
	                                		"/enseignant/**",
	                                		"/admin/**",
	                                		"/api/v1/**",
	                                		"/api/v1/auth/**",
	                                        "/v2/api-docs",
	                                        "/v3/api-docs",
	                                        "/v3/api-docs/**",
	                                        "/swagger-resources",
	                                        "/swagger-resources/**",
	                                        "/configuration/ui",
	                                        "/configuration/security",
	                                        "/swagger-ui/**",
	                                        "/webjars/**",
	                                        "/swagger-ui.html"

	                                ).permitAll()
	                                //.requestMatchers(HttpMethod.DELETE,"/api/v1/**").hasRole("ADMIN")
	                                .anyRequest().authenticated())
	                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
	                .authenticationProvider(authenticationProvider).addFilterBefore(
	                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }
}
  
