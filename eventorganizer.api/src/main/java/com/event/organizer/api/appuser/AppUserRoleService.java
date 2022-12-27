package com.event.organizer.api.appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserRoleService {

    private final AppUserRoleRepository appUserRoleRepository;

    AppUserRole getRole(String role) {
        switch (role) {
            case AppUserRole.ADMIN:
                return appUserRoleRepository.getAdminRole();
            case AppUserRole.ORGANIZER:
                return appUserRoleRepository.getOrganizerRole();
            case AppUserRole.CLIENT:
                return appUserRoleRepository.getClientRole();
            case AppUserRole.USER:
                return appUserRoleRepository.getUserRole();
            default:
                return appUserRoleRepository.getUserRole();
        }
    }
}
