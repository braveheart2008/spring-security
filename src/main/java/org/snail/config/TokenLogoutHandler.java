package org.snail.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.snail.common.TokenManager;
import org.snail.utils.R;
import org.snail.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class TokenLogoutHandler implements LogoutHandler {
	
	private TokenManager tokenManager;
	private RedisTemplate<String, Object> redisTemplate;
	
	public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate){
		this.tokenManager = tokenManager;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String token = request.getHeader("token");
		if(token != null){
			tokenManager.clearToken(token);
			String name = tokenManager.parseToken(token);
			redisTemplate.delete(name);
		}
		ResponseUtil.out(response, R.ok());
	}

}
