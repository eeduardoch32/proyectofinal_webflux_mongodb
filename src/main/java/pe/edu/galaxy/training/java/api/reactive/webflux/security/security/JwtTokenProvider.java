package pe.edu.galaxy.training.java.api.reactive.webflux.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioDto;
import pe.edu.galaxy.training.java.api.reactive.webflux.security.dto.UsuarioSubjectDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class JwtTokenProvider {

	private final long EXPIRE_DURATION = 24 * 60 * 60 * 1_000; // 24 hour

	@Value("${custom.jwt.secret}")
	private String SECRET_KEY;

	public String generateToken(UsuarioDto usuarioDto) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			
			String subject = mapper.writeValueAsString(new UsuarioSubjectDto(usuarioDto.getId(), usuarioDto.getUsuario()));
			
			//String usu="{\"id\":\"65e2b28f6c52224b4e9b012a\",\"usuario\":\"anovoa\"}";
			
			List<GrantedAuthority> roles = new ArrayList<>();
			for (String role : usuarioDto.getRoles()) {
				roles.add(new SimpleGrantedAuthority(role.toUpperCase()));
			}
			
			return Jwts.builder()
	                .subject(subject)
	                .claim("authorities", roles)
	                .issuedAt(new Date())
	                .expiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
	                .signWith(getKey(SECRET_KEY))
	                .compact();
		
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return "";
		}

	}

	public boolean validate(String token) {
		try {
			Jwts.parser().verifyWith(getKey(SECRET_KEY)).build().parseSignedClaims(token).getPayload().getSubject();
			return true;
		} catch (ExpiredJwtException e) {
			log.error("Token expired");
		} catch (UnsupportedJwtException e) {
			log.error("Token unsupported");
		} catch (MalformedJwtException e) {
			log.error("Token malformed");
		} catch (SignatureException e) {
			log.error("Signature error");
		} catch (IllegalArgumentException e) {
			log.error("illegal args");
		}
		return false;
	}

	public Claims getClaims(String token) {
		return Jwts.parser().verifyWith(getKey(SECRET_KEY)).build().parseSignedClaims(token).getPayload();
	}

	private SecretKey getKey(String secret) {
		byte[] secretBytes = Decoders.BASE64URL.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
}