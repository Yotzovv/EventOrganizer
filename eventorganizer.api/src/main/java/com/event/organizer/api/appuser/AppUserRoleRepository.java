package com.event.organizer.api.appuser;
/**DB search for users by roles.*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {
    Optional<AppUserRole> findByRole(String role);

    @Query("SELECT a FROM AppUserRole a WHERE a.role = 'ADMIN'")
    AppUserRole getAdminRole();

    @Query("SELECT a FROM AppUserRole a WHERE a.role = 'USER'")
    AppUserRole getUserRole();

    @Query("SELECT a FROM AppUserRole a WHERE a.role = 'CLIENT'")
    AppUserRole getClientRole();

    @Query("SELECT a FROM AppUserRole a WHERE a.role = 'ORGANIZER'")
    AppUserRole getOrganizerRole();
}
