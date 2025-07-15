package com.marketplace.demo.configs;

import com.marketplace.demo.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        //check if we have JWT Token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        String path = request.getServletPath();
        System.out.println("🔍 Incoming request path: " + path);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ No token found — skipping authentication.");
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        //check if user is authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);


            List<GrantedAuthority> authorities = jwtService.getAuthoritiesWithRoles(jwt);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities  // ✅ use roles from token
            );


            //check if token is still valid
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities());


            //give details to token
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            //update security context holder
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request,response);

    }
}
