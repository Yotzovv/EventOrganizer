package com.event.organizer.api.security.config;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;

class AdminConfig {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "wimgrenade";


    static AppUser createAdminUser() {
        AppUser appUser = new AppUser();
        appUser.setEmail(ADMIN_EMAIL);
        appUser.setUsername(ADMIN_USERNAME);
        appUser.setName(ADMIN_USERNAME);
        appUser.setPassword(ADMIN_PASSWORD);
        appUser.setEnabled(true);
        appUser.setRole(AppUserRole.ADMIN);
        return appUser;
    }
}
