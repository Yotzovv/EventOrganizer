package com.event.organizer.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.filter.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/refreshToken")
@AllArgsConstructor
@CrossOrigin
public class TokenController {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_ERROR = "Error during authorization";
    private static final String ERROR_HEADER = "error";
    private static final String ERROR_MESSAGE_KEY = "error_message";


    private final AppUserService appUserService;

    @GetMapping
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            try {
                String refreshToken = authorizationHeader.substring(BEARER_PREFIX.length());
                Algorithm algorithm = JwtConfig.getAlgotithm();
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String username = decodedJWT.getSubject();
                AppUser user = (AppUser) appUserService.loadUserByUsername(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("access_token", accessToken);
                tokenMap.put("refresh_token", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
            } catch (Exception e) {

                response.setHeader(ERROR_HEADER, AUTHORIZATION_ERROR);
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put(ERROR_MESSAGE_KEY, AUTHORIZATION_ERROR);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
