package com.event.organizer.api.filter;
/**Config for Jwt.*/
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.event.organizer.api.security.config.WebSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_ERROR = "Error during authorization";
    private static final String ERROR_HEADER = "error";
    private static final String ERROR_MESSAGE_KEY = "error_message";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().equals(WebSecurityConfig.LOGIN_URL)
                || httpServletRequest.getServletPath().equals(WebSecurityConfig.REGISTRATION_URL)
                || httpServletRequest.getServletPath().equals(WebSecurityConfig.REFRESH_TOKEN_URL)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
                try {
                    String token = authorizationHeader.substring(BEARER_PREFIX.length());
                    Algorithm algorithm = JwtConfig.getAlgotithm();
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList();
                    Stream.of(roles).forEach(role ->
                    {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } catch (Exception e) {
                    log.info(AUTHORIZATION_ERROR);
                    httpServletResponse.setHeader(ERROR_HEADER, AUTHORIZATION_ERROR);
                    httpServletResponse.setStatus(FORBIDDEN.value());

                    Map<String, String> error = new HashMap<>();
                    error.put(ERROR_MESSAGE_KEY, AUTHORIZATION_ERROR);

                    httpServletResponse.setContentType(APPLICATION_JSON_VALUE);

                    new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}
