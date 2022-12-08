package org.snail.common;

import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {
	
	private long expiration = 24*3600*1000;
	private String key = "snail";
	
	public String genToken(String usernmae){
		String token = Jwts.builder().setSubject(usernmae)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, key).compressWith(CompressionCodecs.GZIP).compact();
		return token;
	}
	
	public String parseToken(String token){
		String uname = Jwts.parser().setSigningKey(key)
				.parseClaimsJws(token).getBody().getSubject();
		
		return uname;
	}
	
	public void clearToken(String token){
		
	}

}
