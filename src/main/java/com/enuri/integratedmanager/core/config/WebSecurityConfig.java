package com.enuri.integratedmanager.core.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String OPEN_IPS = "hasIpAddress('127.0.0.1') or hasIpAddress('') or hasIpAddress('')";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

		 http.authorizeRequests()
		 	.antMatchers("/ip").permitAll()
		    .antMatchers("/**").access(OPEN_IPS)
		    .anyRequest().authenticated()
            .and()
            .csrf()
                .ignoringAntMatchers("/h2-console/**")
            .and()
            .headers()
                .addHeaderWriter(
                    new XFrameOptionsHeaderWriter(
                    		new WhiteListedAllowFromStrategy(Arrays.asList(""))
                    )
                ).frameOptions().sameOrigin();

		 //Forbidden error 403 해결
		 http.cors().and();
		 http.csrf().disable();
    }
}
