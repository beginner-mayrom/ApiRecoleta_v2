package br.com.recoleta.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.recoleta.app.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

	@Override
	@Bean // Sobrescrevendo o método para expor o AuthenticationManager como um Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authenticationProvider());

		/* auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); */
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() 
		.authorizeRequests()
		.antMatchers("/user/registration", "/user/login", "/**").permitAll()
		.anyRequest().authenticated(); 
		
		http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());
	}

	private CustomSessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new CustomSessionAuthenticationStrategy(sessionRegistry());
    }
}
