package com.brocoder.userservice.jwt;

import com.brocoder.userservice.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.brocoder.userservice.constants.SecurityConstants.EXPIRATION_TIME;

@Service
public class JwtUtil {
    @Value("jwt.secret")
    private String secret;

    public String extractUserName(String token){
        return  extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    Predicate<String> isTokenExpired=(token)->{
      return extractExpiration(token).before(new Date());
    };

    BiFunction<Map<String, Object>, String, String> createToken =(claims,subject)->{
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis()+ EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    };

    BiPredicate<String, UserDetails> validateToken=(token, userDetails)->{
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired.test(token));
    };

    public String generateToken(String userName,String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken.apply(claims,userName);

    };
}
