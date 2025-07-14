package com.example.fullstack2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authrizeHttp -> {
                            authrizeHttp.requestMatchers("/").permitAll();
                            authrizeHttp.requestMatchers("/login").permitAll();
                            authrizeHttp.requestMatchers("/register").permitAll();
                            authrizeHttp.requestMatchers("/logout").permitAll();
                            authrizeHttp.requestMatchers("/webjars/**").permitAll();
                            authrizeHttp.requestMatchers("/h2-console/**").permitAll();
                            authrizeHttp.requestMatchers("/css/**").permitAll();
                            authrizeHttp.requestMatchers("/js/**").permitAll();
                            authrizeHttp.anyRequest().authenticated();
                        }
                )
                .formLogin(form -> form.disable()) // 🔥 disable default form login
                .httpBasic(httpBasic -> httpBasic.disable())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // ✅ Redirect to login page if no token or unauthorized
                            response.sendRedirect("/login");
                        })
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .csrf(customizer -> customizer.disable())
                .headers(headers -> headers
                    .frameOptions(frame -> frame.disable()) // <== THIS IS THE KEY FIX
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }
}
