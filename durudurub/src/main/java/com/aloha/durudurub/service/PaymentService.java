package com.aloha.durudurub.service;

import com.aloha.durudurub.dto.Payment;

/**
 * 결제 서비스
 */
public interface PaymentService {

    void createOrder(int userNo, String orderId, String orderName, int amount);

    Payment selectByOrderId(String orderId);

    void markApproved(String orderId, String paymentKey);
}
