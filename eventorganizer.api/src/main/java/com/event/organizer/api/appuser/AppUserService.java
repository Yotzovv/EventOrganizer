package com.event.organizer.api.appuser;

import com.event.organizer.api.security.config.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    private static final String USER_CREATED = "User %s is created";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void editAccount(AppUser editedUser) throws UsernameNotFoundException {
        if(editedUser == null){
            throw new UsernameNotFoundException("User is not found.");
        }
        //TODO: blocked by ev3
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

    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("User exists");
        }

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser.setEnabled(true);

        userRepository.save(appUser);

        return String.format(USER_CREATED, appUser.getUsername());
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
