package com.event.organizer.api.appuser.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (!confirmationToken.isPresent()) {
            throw new IllegalStateException("Confirmation token is not present");
        }
        return confirmationToken.get();
    }

    public void setConfirmedAt(ConfirmationToken confirmationToken) {
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }
}
