package br.com.bureau.gateway.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
	
	private String secret = "br.com.bureau.gateway";

	private Long expiration = 35000000l;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean isValidToken(String token) {
		Claims claims = this.getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirateDate = claims.getExpiration();
			Date now = new Date();
			return username != null && expirateDate != null && now.before(expirateDate);
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = this.getClaims(token);
		return claims != null ? claims.getSubject() : null;
	}

}
