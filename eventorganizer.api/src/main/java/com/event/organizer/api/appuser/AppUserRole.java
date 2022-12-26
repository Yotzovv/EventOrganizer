package com.event.organizer.api.appuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppUserRole {

    public static final String[] ROLE_TYPES = {"ADMIN", "ORGANIZER", "CLIENT", "USER"};
    public static final String ADMIN = "ADMIN";
    public static final String ORGANIZER = "ORGANIZER";
    public static final String CLIENT = "CLIENT";
    public static final String USER = "USER";

    public AppUserRole(String role) {
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String role;
}
