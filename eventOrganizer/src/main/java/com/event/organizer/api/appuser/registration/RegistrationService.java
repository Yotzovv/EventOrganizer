package com.event.organizer.api.appuser.registration;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.registration.token.ConfirmationToken;
import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;

    private final AppUserService appUserService;

    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest registrationRequest) {
        boolean validEmail = emailValidator.test(registrationRequest.getEmail());

        if (!validEmail) {
            throw new IllegalStateException();
        }

        // if everything is valid sign up user

        ConfirmationToken confirmationToken = appUserService.signUpUser(new AppUser(
            registrationRequest.getEmail(),
            registrationRequest.getEmail(),
            registrationRequest.getEmail(),
            registrationRequest.getPassword(),
            AppUserRole.USER
        ));

        if (confirmationToken == null) {
            throw new IllegalStateException("User failed to register");
        }

        confirmToken(confirmationToken.getToken());

        return confirmationToken.getToken();
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        //if (confirmationToken.getConfirmedAt() != null) {
        //    throw new IllegalStateException("email already confirmed");
        //}

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        int id = appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
