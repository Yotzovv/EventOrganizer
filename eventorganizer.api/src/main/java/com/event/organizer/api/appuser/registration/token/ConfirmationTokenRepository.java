package com.event.organizer.api.appuser.registration.token;

import java.util.Optional;

import com.event.organizer.api.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    Optional<ConfirmationToken> findByTokenAndAppUser(String token, AppUser user);
}
