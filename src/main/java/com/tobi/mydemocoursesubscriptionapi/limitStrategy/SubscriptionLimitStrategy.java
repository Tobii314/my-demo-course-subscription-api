package com.tobi.mydemocoursesubscriptionapi.limitStrategy;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;

public interface SubscriptionLimitStrategy {
    int getLimit();
    UserStatus getStatus();
}
