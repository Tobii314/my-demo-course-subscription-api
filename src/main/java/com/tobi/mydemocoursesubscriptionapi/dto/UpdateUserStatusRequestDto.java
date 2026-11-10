package com.tobi.mydemocoursesubscriptionapi.dto;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequestDto {
    private UserStatus status;
}
