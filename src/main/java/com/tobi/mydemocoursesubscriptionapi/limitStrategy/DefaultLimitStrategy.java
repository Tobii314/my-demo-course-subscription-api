package com.tobi.mydemocoursesubscriptionapi.limitStrategy;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class DefaultLimitStrategy implements SubscriptionLimitStrategy {
    @Override
    public int getLimit() {
        return 3;
    }

    @Override
    public UserStatus getStatus() {
        return UserStatus.DEFAULT;
    }
}
