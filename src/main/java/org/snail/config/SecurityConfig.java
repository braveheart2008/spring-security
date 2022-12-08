package org.snail.config;

import org.snail.common.TokenManager;
import org.snail.filter.TokenAuthFilter;
import org.snail.filter.TokenLoginFilter;
import org.snail.utils.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private TokenManager tokenManager;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
//		http.exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint());
		
		http.formLogin().permitAll();
		
		http.logout().logoutUrl("logout").addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate));
		
		http.authorizeRequests()
			.antMatchers("/manager").hasAuthority("manager")
			.antMatchers("/leader").hasAnyAuthority("manager,leader")
			.anyRequest().authenticated();
		
		http.csrf().disable();
		
		http.addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManagerBean()))
		.addFilter(new TokenAuthFilter(tokenManager, redisTemplate, authenticationManagerBean()));
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/**");
	}

	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	

}
