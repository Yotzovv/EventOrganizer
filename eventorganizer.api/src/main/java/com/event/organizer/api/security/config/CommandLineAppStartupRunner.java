package com.event.organizer.api.security.config;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final AppUserService appUserService;

    @Override
    public void run(String... args) throws Exception {
        AppUser admin = AdminConfig.createAdminUser();
        if (appUserService.findUserByEmail(admin.getEmail()).isPresent()) {
            log.info("Admin user exists don't do anything");
            return;
        }
        appUserService.saveAppUser(admin);
        log.info("Admin user saved");
    }
}
