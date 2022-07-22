package com.dps_admin.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dps_admin.service.AdminDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AdminDetailsServiceImpl adminDetailsServiceImpl;
//	private JwtUtil jwtHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
	}
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String loginPage = "/auth/login";
		String logoutPage = "/logout";
		http.authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers(loginPage).permitAll().antMatchers("/admin/**")
				.authenticated().and().csrf().disable().formLogin();
	}

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/templates/**", "/fonts/**", "/css/**", "/img/**", "/pdf/**", "/js/**", "/images/**", "/layout/**");
	}
}