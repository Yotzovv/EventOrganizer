package com.event.organizer.api.appuser;

import lombok.Getter;

@Getter
public enum AppUserRole {

    ADMIN("ADMIN"),
    ORGANIZER("ORGANIZER"),
    CLIENT("CLIENT"),
    USER("USER");

    public final String role;

    AppUserRole(String role1) {
        this.role = role1;
    }
}
