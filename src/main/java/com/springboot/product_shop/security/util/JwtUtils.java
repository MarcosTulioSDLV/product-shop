package com.springboot.product_shop.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt-expiration-time}")
    private Integer expirationTime;

    public String createToken(Authentication authentication){
        try {
            String username= authentication.getName();
            Algorithm algorithm= Algorithm.HMAC256(secretKey);

            Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
            String authoritiesAsStr= authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
             //authorities= AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesAsStr);
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(username)
                    .withClaim("authorities",authoritiesAsStr)
                    .withIssuedAt(new Date())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new JWTCreationException("Error creating JWT token!",exception);
        }
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationTime * 60 * 1000);
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm= Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token);
        }catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new JWTCreationException("JWT Token Invalid,not authorized",exception);
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public Claim extractClaim(DecodedJWT decodedJWT,String claimName){
       return decodedJWT.getClaim(claimName);
    }


}
