package com.travel.plan.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


        @Bean
        public WebSecurityCustomizer webSecurityCustomizer(){
            return new WebSecurityCustomizer() {
                @Override
                public void customize(WebSecurity web) {
                    web.ignoring().requestMatchers("/favicon.ico","/error");
                }
            };
        }


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

            return http
                    .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .build();
        }


}
