package org.serratec.backend.projetoFinal.config;

import org.serratec.backend.projetoFinal.security.AuthService;
import org.serratec.backend.projetoFinal.security.JWTAuthenticationFilter;
import org.serratec.backend.projetoFinal.security.JWTAuthorizationFilter;
import org.serratec.backend.projetoFinal.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthService service;
	
	@Autowired
	JWTUtil jwtUtil;
	
	private static final String[] AUTH_WHITELIS = {"/categoria/*", "/cliente/", "/endereco/", "/pedido/**", "/produto/","/pedidoproduto/*","**/login/**"};
	

	
	@Override
	protected void configure (HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers(AUTH_WHITELIS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.antMatchers(HttpMethod.DELETE).permitAll()
		.antMatchers(HttpMethod.POST).permitAll()
		.antMatchers(HttpMethod.PUT).permitAll()
		.anyRequest().authenticated();
		http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder());
	}

}
