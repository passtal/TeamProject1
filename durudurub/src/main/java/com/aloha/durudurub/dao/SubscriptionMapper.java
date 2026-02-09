package com.aloha.durudurub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Subscription;

/**
 * 구독 매퍼
 */
@Mapper
public interface SubscriptionMapper {

    Subscription selectByUserNo(@Param("userNo") int userNo);

    int upsertActiveSubscription(
        @Param("userNo") int userNo,
        @Param("startDate") java.util.Date startDate,
        @Param("endDate") java.util.Date endDate
    );
}
