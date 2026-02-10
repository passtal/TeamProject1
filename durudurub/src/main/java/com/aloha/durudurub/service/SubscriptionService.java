package com.aloha.durudurub.service;

import com.aloha.durudurub.dto.Subscription;

/**
 * 구독 서비스
 */
public interface SubscriptionService {

    Subscription selectByUserNo(int userNo);

    void activateSubscription(int userNo, int periodMonths);
}
