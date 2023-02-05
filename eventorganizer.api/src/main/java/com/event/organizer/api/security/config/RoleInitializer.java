package com.event.organizer.api.security.config;
/**Saves a change in a user's account status*/
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Order(1)
public class RoleInitializer implements CommandLineRunner {

    private final AppUserRoleRepository appUserRoleRepository;

    @Override
    public void run(String... args) {
        for (String roleType : AppUserRole.ROLE_TYPES) {
            Optional<AppUserRole> role = appUserRoleRepository.findByRole(roleType);
            if (!role.isPresent()) {
                appUserRoleRepository.save(new AppUserRole(roleType));
            }
        }
    }
}
