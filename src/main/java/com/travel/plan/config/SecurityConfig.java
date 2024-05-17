package com.travel.plan.config;


import com.travel.plan.domain.User;
import com.travel.plan.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/auth/login","auth/signup").permitAll()
                        .anyRequest().authenticated()
                    )
                    .formLogin((form) ->form
                            .loginPage("/auth/login")
                            .loginProcessingUrl("/auth/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                    )
                    .rememberMe((rm)-> rm.rememberMeParameter("remember")
                            .alwaysRemember(false)
                            .tokenValiditySeconds(2592000)
                    )
                    .csrf(AbstractHttpConfigurer::disable)
                    .build();
        }


        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository) {
            return username -> {
                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
                return new UserPrincipal(user);
            };
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();

        }

}
