package com.libra.bookshopauth.config;

import com.libra.bookshopauth.config.exceptions.CustomAccessDeniedHandler;
import com.libra.bookshopauth.config.exceptions.CustomBasicAuthenticationEntryPoint;
import com.libra.bookshopauth.config.filters.CsrfCookieFilter;
import com.libra.bookshopauth.config.filters.JWTTokenGeneratorFilter;
import com.libra.bookshopauth.config.filters.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class ProjectSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestHandler = new CsrfTokenRequestAttributeHandler(); // To get the CSRF token from the request header
        http
                //.securityContext(securityContext -> securityContext.requireExplicitSave(false)) // Don't save the session in Context holder
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Always create a new session
                //.sessionManagement(session -> session.invalidSessionUrl("/login?sessionExpired=true")) // Invalid session URL and wi should add this url to .requestMatchers()..permitAll()
                //.requiresChannel(channel -> channel.anyRequest().requiresSecure()) // Only HTTPS
                //.requiresChannel(channel -> channel.anyRequest().requiresInsecure()) // Only HTTP
                //.csrf(AbstractHttpConfigurer::disable) // Disable CSRF

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/customers/**")
                        .csrfTokenRequestHandler(csrfTokenRequestHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) // Add CSRF filter to read CSRF token
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class) // Add JWT filter to generate JWT token
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class) // Add JWT filter to validate JWT token
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/test", "/test/**", "/error", "/users", "/actuator/**").permitAll()
                        .requestMatchers("/customers/**","/authors").authenticated()
                        .requestMatchers("/users/admin").hasAuthority(ADMIN)
                );

        /*http.logout(logout -> logout.logoutSuccessUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"));*/ // Logout URL

        http.formLogin(Customizer.withDefaults());

        //If we want to use custom login form:
        /*http.formLogin(flc -> flc.loginPage("/login")
                .usernameParameter("email") // Use this to configure custom login form username. Default is "username"
                .passwordParameter("password") // Use this to configure custom login form password. Default is "password"
                .defaultSuccessUrl("/home"));*/

        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler()));// It is Global configuration
        return http.build();
    }

    // Instead of this, we will use custom implementation @see BookshopUserDetailService
    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
       *//* UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$Zpf3j1.Kw1StICsO0zYv3O3Td4EHMJ8xhjHdJ5jHdbvBqO13Zqmou")
                .roles(ADMIN).
                build();
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$12$md74elUB4.8mx0czv60RmufEZlp/EDz52WXpnYGDa0ZCfSVKY4JZO")
                .roles(USER)
                .build();
        return new InMemoryUserDetailsManager(admin, user);*//*

        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Provides a bean for checking if a password has been compromised
     * using the Have I Been Pwned API.
     *
     * @return a CompromisedPasswordChecker instance configured with
     * HaveIBeenPwnedRestApiPasswordChecker.
     */
   /* @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }*/
}
