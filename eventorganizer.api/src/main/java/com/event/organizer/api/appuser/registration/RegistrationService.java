package com.event.organizer.api.appuser.registration;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
class RegistrationService {

    private EmailValidator emailValidator;

    private final AppUserService appUserService;

    String register(RegistrationRequest registrationRequest) {
        boolean validEmail = emailValidator.test(registrationRequest.getEmail());

        if (!validEmail) {
            throw new IllegalStateException();
        }

        return appUserService.signUpUser(new AppUser(
                registrationRequest.getEmail(),
                registrationRequest.getEmail(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                AppUserRole.USER
        ));
    }
}
