package com.tobi.mydemocoursesubscriptionapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequestDto {
    private Long userId;
    private Long courseId;
}
