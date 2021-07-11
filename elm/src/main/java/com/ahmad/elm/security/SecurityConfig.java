package com.ahmad.elm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // allow all http methods
                .antMatchers("/user", "/user/**" ).permitAll()
                // allow just GET method
                .antMatchers(HttpMethod.GET, "/article" , "/article/{id}/comment" , "/article/{id}").permitAll()
                // allow POST method for user with role USER
                .antMatchers( HttpMethod.POST ,"/article" , "/article/**").hasRole("USER")
                .antMatchers( HttpMethod.DELETE ,"/article/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT , "/article/{id}/like" , "/article/{id}/dislike").hasRole("USER")
                .antMatchers(HttpMethod.PUT ,"/article/{id}/enable" , "/article/{id}/disable").hasRole("ADMIN")
                .anyRequest().authenticated().and().httpBasic();


    }

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
}
