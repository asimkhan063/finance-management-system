package com.asim.finance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.asim.finance.jwt.JwtAuthenticationFilter;
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;
    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.successHandler = successHandler;
    }


   // public SecurityConfig(CustomUserDetailsService userDetailsService) {
     //   this.userDetailsService = userDetailsService;
    //}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .userDetailsService(userDetailsService)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(

                                "/",

                                "/login",

                                "/register",

                                "/register/save",

                                "/api/auth/**",

                                "/swagger-ui/**",

                                "/v3/api-docs/**",

                                "/css/**",

                                "/js/**",

                                "/images/**",

                                "/forgot-password",
                                "/send-otp",
                                "/verify-otp",
                                "/reset-password"

                        ).permitAll()



                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")



                        .requestMatchers(
                                "/dashboard/**",
                                "/expenses/**",
                                "/income/**"
                        )
                        .hasAnyRole(
                                "USER",
                                "ADMIN"
                        )



                        .anyRequest()
                        .authenticated()


                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/dashboard", true)

                        .permitAll()

                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()

                );

        return http.build();

    }
/*
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }*/

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();

    }

}