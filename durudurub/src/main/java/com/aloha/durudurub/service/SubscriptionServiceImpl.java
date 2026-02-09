package com.aloha.durudurub.service;

import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.SubscriptionMapper;
import com.aloha.durudurub.dto.Subscription;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 구독 서비스 구현체
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(SubscriptionMapper subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public Subscription selectByUserNo(int userNo) {
        return subscriptionMapper.selectByUserNo(userNo);
    }

    @Override
    public void activateSubscription(int userNo, int periodMonths) {
        Subscription existing = subscriptionMapper.selectByUserNo(userNo);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = now;

        if (existing != null && existing.getEndDate() != null) {
            LocalDateTime existingEnd = LocalDateTime.ofInstant(
                existing.getEndDate().toInstant(),
                ZoneId.systemDefault()
            );
            if (existingEnd.isAfter(now)) {
                base = existingEnd;
            }
        }

        LocalDateTime newEnd = base.plusMonths(periodMonths);
        Date startDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(newEnd.atZone(ZoneId.systemDefault()).toInstant());

        subscriptionMapper.upsertActiveSubscription(userNo, startDate, endDate);
    }
}
