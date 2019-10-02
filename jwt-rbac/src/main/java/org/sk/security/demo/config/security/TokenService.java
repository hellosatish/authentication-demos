package org.sk.security.demo.config.security;

import java.security.Principal;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.sk.security.demo.db.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
/**
 * 
 * @author satish sharma
 * 
 *  Help with JWT related operations
 * 
 */
@Log4j2
@Component
public class TokenService {

	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.tokenValidityInMinutes}")
	private long tokenValidityInMinutes;
	@Autowired
	private UserDetailsService customUserDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(User usr) {
		Date validity = new Date(new Date().getTime() + (tokenValidityInMinutes * 10000));
		return Jwts.builder().setSubject(usr.getLoginId()).claim("ROLE", usr.getRole())
				.claim("NAME", usr.getFirstName()).signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity)
				.setIssuer("MYAPP").compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getLoginId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getLoginId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return (claims.getBody().getExpiration().before(new Date())) ? false : true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public String getTokenValue(String authHeaderValue) {
	return (StringUtils.hasText(authHeaderValue) && authHeaderValue.startsWith("Bearer ")) ? authHeaderValue.substring(7, authHeaderValue.length())	: null;
		}
}
