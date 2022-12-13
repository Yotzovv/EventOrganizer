package com.event.organizer.api.filter;

import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@AllArgsConstructor
@Order(1)
public class DefaultTokenFilter implements Filter {

    private static final String TOKEN_HEADER = "Cookie";

    private ConfirmationTokenService confirmationTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN_HEADER);
        confirmationTokenService.validateToken(token);

        filterChain.doFilter(httpServletRequest, response);
    }


}
