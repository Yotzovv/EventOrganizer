package com.event.organizer.api.security.config;
/**Sets an admin status to an account .*/
import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class AdminConfig {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "wimgrenade";

    private final PasswordEncoder passwordEncoder;
    private final AppUserRoleRepository appUserRoleRepository;

    AppUser createAdminUser() {
        AppUser appUser = new AppUser();
        appUser.setEmail(ADMIN_EMAIL);
        appUser.setUsername(ADMIN_USERNAME);
        appUser.setName(ADMIN_USERNAME);

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(ADMIN_PASSWORD);
        appUser.setPassword(encodedPassword);

        appUser.setEnabled(true);
        appUser.setRoles(Collections.singleton(appUserRoleRepository.getAdminRole()));
        return appUser;
    }

    public static boolean isSuperUserAdmin(String email) {
        return email.equals(ADMIN_EMAIL);
    }
}
