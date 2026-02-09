package com.aloha.durudurub.service;

import org.springframework.stereotype.Service;

import com.aloha.durudurub.dao.PaymentMapper;
import com.aloha.durudurub.dto.Payment;

/**
 * 결제 서비스 구현체
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @Override
    public void createOrder(int userNo, String orderId, String orderName, int amount) {
        paymentMapper.insertOrder(userNo, orderId, orderName, amount, "READY");
    }

    @Override
    public Payment selectByOrderId(String orderId) {
        return paymentMapper.selectByOrderId(orderId);
    }

    @Override
    public void markApproved(String orderId, String paymentKey) {
        paymentMapper.markApproved(orderId, paymentKey);
    }
}
