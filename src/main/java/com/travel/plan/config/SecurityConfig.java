package com.travel.plan.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


        @Bean
        public WebSecurityCustomizer webSecurityCustomizer(){
            return new WebSecurityCustomizer() {
                @Override
                public void customize(WebSecurity web) {
                    web.ignoring().requestMatchers("/favicon.ico","/error")
                            .requestMatchers(PathRequest.toH2Console());
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
