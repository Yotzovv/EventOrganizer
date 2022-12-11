package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.registration.token.ConfirmationToken;
import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;
import com.event.organizer.api.model.dto.LoginBody;
import com.event.organizer.api.security.config.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class LoginService {

    private AppUserService appUserService;
    private PasswordEncoder passwordEncoder;
    private ConfirmationTokenService confirmationTokenService;


    public String loginUser(LoginBody loginBody) {
        String password = loginBody.getPassword();
        String username = loginBody.getEmail();

        AppUser user = (AppUser) appUserService.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with current credentials");
        }

        boolean passwordMatches = passwordEncoder.bCryptPasswordEncoder()
                .matches(password, user.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("User not found with current credentials");
        }

        //TODO refresh token if exists
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), //this in config
                LocalDateTime.now(), //this in config
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }


    private void refreshTokenIfExists() {


    }


}
