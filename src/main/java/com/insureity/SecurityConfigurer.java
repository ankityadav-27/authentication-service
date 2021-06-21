package com.insureity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.insureity.service.AgentDetailsService;

/**
 * spring security configurations
 * 
 *
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	AgentDetailsService emsuserDetailsService;
	

	/**
	 * Registers details in builder
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		super.configure(auth);
		auth.userDetailsService(emsuserDetailsService);

	}

	/**
	 * applies http filters for access
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//CSRF stands for Cross-Site Request Forgery
		http.csrf().disable().authorizeRequests()
					.antMatchers("/**").permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.exceptionHandling().and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * extra configurations for ignoring some urls
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/login", "/h2-console/**");
	}

	/**
	 * Authentication manager bean
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
