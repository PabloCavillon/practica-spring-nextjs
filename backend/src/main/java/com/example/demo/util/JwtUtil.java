package com.example.demo.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String generateToken(String username, String role) {
    return Jwts.builder()
              .setSubject(username)
              .claim("role", role)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
              .signWith(SECRET_KEY)
              .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
  }

  public String extractRole(String token) {
    return (String) Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .get("role");
  }

  public boolean isTokenValid(String token, String username) {
    String extractedUsername = extractUsername(token);
    return extractedUsername.equals(username) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getExpiration()
              .before(new Date());
  }

  public String refreshToken(String token) {
    String username = extractUsername(token);
    String role = extractRole(token);

    return Jwts.builder()
              .setSubject(username)
              .claim("role", role)
              .setIssuedAt(new Date()) 
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
              .signWith(SECRET_KEY)
              .compact();
  }
} 
