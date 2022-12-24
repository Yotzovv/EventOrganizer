package com.event.organizer.api.security.config;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class AdminConfig {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "wimgrenade";

    private final PasswordEncoder passwordEncoder;

    AppUser createAdminUser() {
        AppUser appUser = new AppUser();
        appUser.setEmail(ADMIN_EMAIL);
        appUser.setUsername(ADMIN_USERNAME);
        appUser.setName(ADMIN_USERNAME);

        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(ADMIN_PASSWORD);
        appUser.setPassword(encodedPassword);

        appUser.setEnabled(true);
        appUser.setRole(AppUserRole.ADMIN);
        return appUser;
    }
}
