package com.aloha.durudurub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Payment;

/**
 * 결제 매퍼
 */
@Mapper
public interface PaymentMapper {

    int insertOrder(
        @Param("userNo") int userNo,
        @Param("orderId") String orderId,
        @Param("orderName") String orderName,
        @Param("amount") int amount,
        @Param("status") String status
    );

    Payment selectByOrderId(@Param("orderId") String orderId);

    int markApproved(
        @Param("orderId") String orderId,
        @Param("paymentKey") String paymentKey
    );
}
