package com.gsg.demo.springbootgraphqljwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsg.demo.springbootgraphqljwt.authentication.DefaultUserDetailsService;
import com.gsg.demo.springbootgraphqljwt.config.JwtConfig;
import com.gsg.demo.springbootgraphqljwt.filter.JwtTokenVerifierFilter;
import com.gsg.demo.springbootgraphqljwt.filter.JwtUsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_ERASER;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_READER;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_WRITER;


@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DefaultUserDetailsService defaultUserDetailsService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, objectMapper))
                .addFilterAfter(new JwtTokenVerifierFilter(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/gui").permitAll()
                .antMatchers("/graphql").hasAnyRole(BOOK_READER.name(), BOOK_WRITER.name(), BOOK_ERASER.name())
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(defaultUserDetailsService);
        return provider;
    }

}