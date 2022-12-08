package org.snail.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snail.common.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class TokenAuthFilter extends BasicAuthenticationFilter {
	
	private RedisTemplate<String, Object> redisTemplate;
	
	private TokenManager tokenManager;

	public TokenAuthFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, 
			AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 UsernamePasswordAuthenticationToken authResult = verifyToken(request);
		 if(authResult != null){
			 SecurityContextHolder.getContext().setAuthentication(authResult);
		 }
		 chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken verifyToken(HttpServletRequest request){
		UsernamePasswordAuthenticationToken authentication = null;
		String token = request.getHeader("token");
		if(token != null){
			String name = tokenManager.parseToken(token);
			@SuppressWarnings("unchecked")
			List<String> permissions = (List<String>) redisTemplate.opsForValue().get(name);
			List<GrantedAuthority> authorityList = new ArrayList<>();
			for(String permission : permissions){
				authorityList.add(new SimpleGrantedAuthority(permission));
			}
			
			authentication = new UsernamePasswordAuthenticationToken(name, token, authorityList);
		}
		
		return authentication;
	}
	
	

}
