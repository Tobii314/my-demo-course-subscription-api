package com.tobi.mydemocoursesubscriptionapi.limitStrategy;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class PremiumLimitStrategy implements SubscriptionLimitStrategy {
    @Override
    public int getLimit() {
        return 10;
    }

    @Override
    public UserStatus getStatus() {
        return UserStatus.PREMIUM;
    }
}
