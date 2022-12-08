package org.snail.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snail.common.TokenManager;
import org.snail.utils.R;
import org.snail.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

	private TokenManager tokenManager;
	private RedisTemplate<String, Object> redisTemplate;
	
	public TokenLoginFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, 
			AuthenticationManager authenticationManager){
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
		setAuthenticationManager(authenticationManager);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User)authResult.getPrincipal();
		String name = user.getUsername();
		String token = tokenManager.genToken(name);
		
		Collection<GrantedAuthority> authories = user.getAuthorities();
		List<String> permissions = new ArrayList<>();
		for(GrantedAuthority authority : authories){
			permissions.add(authority.getAuthority());
		}
		
		redisTemplate.opsForValue().set(name, permissions, 60, TimeUnit.SECONDS);
		
		response.addHeader("token", token);
		
		R r = R.ok();
		r.getData().put("token", token);
		
		ResponseUtil.out(response, r);
	}


	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		ResponseUtil.out(response, R.error());
	}
	
	

}
