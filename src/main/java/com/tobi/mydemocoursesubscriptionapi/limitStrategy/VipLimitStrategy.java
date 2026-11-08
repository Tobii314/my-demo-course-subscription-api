package com.tobi.mydemocoursesubscriptionapi.limitStrategy;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class VipLimitStrategy implements SubscriptionLimitStrategy {
    @Override
    public int getLimit() {
        return 6;
    }

    @Override
    public UserStatus getStatus() {
        return UserStatus.VIP;
    }
}
