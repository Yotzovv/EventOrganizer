package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;

import com.event.organizer.api.appuser.registration.token.ConfirmationToken;
import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.dto.LogoutBody;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class LogoutService {

    private AppUserService appUserService;
    private ConfirmationTokenService confirmationTokenService;

    public String logoutUser(LogoutBody logoutBody) throws EventOrganizerException {
        String username = logoutBody.getEmail();

        AppUser user = (AppUser) appUserService.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String token = logoutBody.getToken();
        // if token exists and is assigned to this user
        // then delete the token, after that every next request will be forbidden since token does not exist
        Optional<ConfirmationToken> tokenOptional = confirmationTokenService.getConfirmationTokenByTokenAndUser(token, user);

        if (!tokenOptional.isPresent()) {
            throw new EventOrganizerException("Invalid data");
        }

        ConfirmationToken confirmationToken = tokenOptional.get();
        confirmationTokenService.deleteToken(confirmationToken);
        return "Success";
    }
}
