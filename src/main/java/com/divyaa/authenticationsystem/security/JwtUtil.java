package com.divyaa.authenticationsystem.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Divyaa P
 */
@Component
public class JwtUtil {

    private final Environment environment;

    @Autowired
    public JwtUtil(Environment environment) {
        this.environment = environment;
    }

    public String getAccessToken(String subject){
        return Jwts.builder()
                .claim("type", "Access token")
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
    }

    public String getRefreshToken(String subject){
        return Jwts.builder()
                .claim("type", "Refresh token")
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
    }

    public boolean hasExpired(String accessToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
                    .parseClaimsJws(accessToken.replace("Bearer ",""));
            return false;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token).getBody().getSubject();
    }
}
