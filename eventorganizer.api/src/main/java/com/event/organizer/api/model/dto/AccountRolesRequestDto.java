package com.event.organizer.api.model.dto;
/**Account roles DTO. Makes a model for the database.*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
public class AccountRolesRequestDto {

    @NotNull
    private String email;
    private List<String> roles;
}
