package com.event.organizer.api.appuser;
/**Creates a table with all account roles.*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppUserRole {

    public static final String ADMIN = "ADMIN";
    public static final String ORGANIZER = "ORGANIZER";
    public static final String CLIENT = "CLIENT";
    public static final String USER = "USER";
    public static final List<String> ROLE_TYPES =  Arrays.asList(ADMIN, ORGANIZER, CLIENT, USER);

    public AppUserRole(String role) {
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String role;
}
