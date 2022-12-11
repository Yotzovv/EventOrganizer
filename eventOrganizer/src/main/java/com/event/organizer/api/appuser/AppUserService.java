package com.event.organizer.api.appuser;

import com.event.organizer.api.appuser.registration.token.ConfirmationToken;
import com.event.organizer.api.appuser.registration.token.ConfirmationTokenService;
import com.event.organizer.api.security.config.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    private final UserRepository userRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final PasswordEncoder passwordEncoder;

    public void editAccount(AppUser editedUser) throws UsernameNotFoundException {
        if(editedUser == null){
            throw new UsernameNotFoundException("User is not found.");
        }
        //blocked by ev3
        //if(editedUser != loggedInUser) {
        //    throw new Exception("Permission denied.")
        //}
         var currentUser = userRepository.findByEmail(editedUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, editedUser.getEmail())));

        userRepository.save(currentUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public ConfirmationToken signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("User exists");
        }

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        //TODO: Send confirmation token this is what ui needs

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15), //this in config
            LocalDateTime.now(), //this in config
            appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        // CHECK IF TOKEN IS SAVED

        // SEND EMAIL
        return confirmationToken;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
