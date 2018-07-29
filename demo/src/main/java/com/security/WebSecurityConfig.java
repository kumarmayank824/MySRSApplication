package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	
	@Autowired
    private UserDetailsService userDetailsService;
    
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
		auth.userDetailsService(userDetailsService);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.authorizeRequests()
    	  .antMatchers("/loginSuccess").access("hasRole('STUDENT') or hasRole('TEACHER')")
    	  .antMatchers("/std-*","/std-*/*/*").access("hasRole('STUDENT')")
    	  .antMatchers("/tch-*").access("hasRole('TEACHER')")
    	  .and()
    	    .formLogin().loginPage("/login")
    	    .usernameParameter("email").passwordParameter("password")
    	  .and()
    	    .csrf()
    	    .and().exceptionHandling().accessDeniedPage("/access_denied");
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**","/fonts/**","/images/**","/vendor/**","/js/**");
	}
    
}