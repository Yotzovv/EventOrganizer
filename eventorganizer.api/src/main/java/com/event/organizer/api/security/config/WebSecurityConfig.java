package com.event.organizer.api.security.config;

import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;
import com.event.organizer.api.filter.DefaultTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/api/v*/events/**",
            "/api/v*/logout/**",
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    private static final String[] PERMIT_ALL = {
            "/api/v*/registration/**",
            "/api/v*/login/**"
    };

    private final AppUserService appUserService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO check how to apply filter custom filter on all request except PERMIT_ALL
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(PERMIT_ALL).permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

    @Bean
    public FilterRegistrationBean<DefaultTokenFilter> defaultTokenFilter() {
        FilterRegistrationBean<DefaultTokenFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        DefaultTokenFilter defaultTokenFilter = new DefaultTokenFilter(confirmationTokenService);
        filterFilterRegistrationBean.setFilter(defaultTokenFilter);
        filterFilterRegistrationBean.setUrlPatterns(List.of(AUTH_WHITELIST));

        return filterFilterRegistrationBean;
    }

}
