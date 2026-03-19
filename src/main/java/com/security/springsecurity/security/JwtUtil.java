package com.security.springsecurity.security;

import com.security.springsecurity.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;


    private SecretKey getSigningKey() {
        byte[] bytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }


    private String createToken(Map<String, Object> claims) {
       return Jwts.builder()
               .setClaims(claims)
               .subject(claims.get("email").toString())
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
               .signWith(getSigningKey())
               .compact();
    }

    public String generateToken(String email, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", roles.stream().map(Role::name).collect(Collectors.toList()));
        return createToken(claims);
    }

    public String extractEmail(String token) {
        return extractClaims(token).get("email").toString();
    }

    private Claims extractClaims(String token) throws JwtException {

        try{
            return Jwts.parser().verifyWith(getSigningKey())
                    .build().parseSignedClaims(token).getPayload();
        }catch(SignatureException e){
            throw new JwtException("Invalid JWT Signature");
        }catch (MalformedJwtException e){
            throw new JwtException("Invalid JWT Token");
        }catch (ExpiredJwtException e){
            throw new JwtException("Expired JWT Token");
        }catch (UnsupportedJwtException e){
            throw new JwtException("Unsupported JWT Token");
        } catch (Exception e){
            throw new JwtException("Invalid JWT Token");
        }

    }

    public Date getExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            boolean isTokenExpired = isTokenExpired(token);

            boolean usernameMatches = userDetails.getUsername().equals(email);
            return !isTokenExpired && usernameMatches;

        }catch (Exception e){
            return false;
        }
    }

}
