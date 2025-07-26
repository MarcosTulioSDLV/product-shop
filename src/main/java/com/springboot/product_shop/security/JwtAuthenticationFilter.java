package com.springboot.product_shop.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.product_shop.security.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken= recoverJwtToken(request);
        if(jwtToken!=null){
            DecodedJWT decodedJWT= jwtUtils.validateToken(jwtToken);
            String username= jwtUtils.extractUsername(decodedJWT);

            //Method 1 (recovering info from the db)
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities= userDetails.getAuthorities();

            //method 2 (recovering info from the token)
            /*String authoritiesAsStr= jwtUtils.extractClaim(decodedJWT,"authorities").asString();
            Collection<? extends GrantedAuthority> authorities= AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesAsStr);*/

            //Method 1 (recovering info from the db)
            var authentication= new UsernamePasswordAuthenticationToken(userDetails,null,authorities);

            //method 2 (recovering info from the token)
            //var authentication= new UsernamePasswordAuthenticationToken(username,null,authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    public String recoverJwtToken(HttpServletRequest request){
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);//Or "Authorization"
        if(jwtToken==null || !jwtToken.startsWith("Bearer ")){
            return null;
        }
        return jwtToken.substring(7);
    }

}
