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
    	  .antMatchers("/loginSuccess","/to*").access("hasRole('ROLE_USER')")
    	  .anyRequest().permitAll()
    	  .and()
    	    .formLogin().loginPage("/login")
    	    .usernameParameter("email").passwordParameter("password")
    	  .and()
    	    .csrf();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**","/fonts/**","/images/**","/vendor/**","/js/**");
	}
    
    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().
    	       withUser("ADMIN").password("ADMIN").
    	       authorities("ROLE_ADMIN");
    }*/
    
}
