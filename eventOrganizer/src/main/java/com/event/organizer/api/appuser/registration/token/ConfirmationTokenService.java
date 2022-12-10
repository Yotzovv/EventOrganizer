package com.event.organizer.api.appuser.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import com.event.organizer.api.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) throws ServletException {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (!confirmationToken.isPresent()) {
            throw new ServletException("Confirmation token is not present");
        }
        return confirmationToken.get();
    }

    public void setConfirmedAt(ConfirmationToken confirmationToken) {
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getConfirmationTokenByTokenAndUser(String token, AppUser appUser) {
        return confirmationTokenRepository.findByTokenAndAppUser(token, appUser);
    }

    public void deleteToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

    public void validateToken(String token) throws ServletException {
        if (StringUtils.isEmpty(token)) {
            throw new ServletException("Invalid token");
        }

        ConfirmationToken confirmationToken = getToken(token);

        if (confirmationToken.getExpiresAt().isAfter(LocalDateTime.now())) {
            throw new ServletException("Token expired");
        }
    }
}
