package com.event.organizer.api.model.dto;
/**Account status DTO. Makes a model for the database.*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
public class AccountStatusRequestDto {

    @NotNull
    private String email;
    private boolean enabled;
}
