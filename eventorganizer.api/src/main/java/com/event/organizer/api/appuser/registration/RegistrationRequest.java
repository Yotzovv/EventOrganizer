package com.event.organizer.api.appuser.registration;
/**Registration model.*/
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String username;
    private final String password;
    private final String location;
    private final String email;
}
