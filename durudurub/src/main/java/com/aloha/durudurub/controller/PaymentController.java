package com.aloha.durudurub.controller;

import java.util.HashMap;
import java.util.Map;
import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.durudurub.dto.Payment;
import com.aloha.durudurub.dto.User;
import com.aloha.durudurub.service.PaymentService;
import com.aloha.durudurub.service.SubscriptionService;
import com.aloha.durudurub.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping
public class PaymentController {

	private final UserService userService;
	private final PaymentService paymentService;
	private final SubscriptionService subscriptionService;

	@Value("${toss.payments.client-key:}")
	private String tossClientKey;

	@Value("${toss.payments.secret-key:}")
	private String tossSecretKey;


	public PaymentController(
		UserService userService,
		PaymentService paymentService,
		SubscriptionService subscriptionService
	) {
		this.userService = userService;
		this.paymentService = paymentService;
		this.subscriptionService = subscriptionService;
	}

	// 결제 메인 페이지
	@GetMapping("/payments")
	public String paymentMain(Model model) {
		model.addAttribute("tossClientKey", tossClientKey);
		return "payments/payment";
	}

	// 일반 결제 샘플 체크아웃
	@GetMapping("/payments/checkout")
	public String paymentCheckout() {
		return "payments/payment/checkout";
	}

	// 결제 성공 페이지
	@GetMapping({"/payments/success", "/payment/success.html"})
	public String paymentSuccess() {
		return "payments/payment/success";
	}

	// 결제 실패 페이지
	@GetMapping({"/payments/fail", "/fail.html"})
	public String paymentFail() {
		return "payments/fail";
	}

	// 브랜드페이 샘플
	@GetMapping("/payments/brandpay/checkout")
	public String brandpayCheckout() {
		return "payments/brandpay/checkout";
	}

	@GetMapping("/payments/brandpay/success")
	public String brandpaySuccess() {
		return "payments/brandpay/success";
	}

	// 위젯 샘플
	@GetMapping("/payments/widget/checkout")
	public String widgetCheckout(Model model) {
		model.addAttribute("tossClientKey", tossClientKey);
		return "payments/widget/checkout";
	}

	@GetMapping("/payments/widget/success")
	public String widgetSuccess() {
		return "payments/widget/success";
	}

	// 결제 승인 (Toss confirm)
	@PostMapping("/confirm/payment")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> confirmPayment(@RequestBody Map<String, Object> payload) {
		String paymentKey = String.valueOf(payload.get("paymentKey"));
		String orderId = String.valueOf(payload.get("orderId"));
		String amount = String.valueOf(payload.get("amount"));

		if (paymentKey == null || orderId == null || amount == null) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "INVALID_REQUEST");
			error.put("message", "paymentKey/orderId/amount is required");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}

		int amountValue;
		try {
			amountValue = Integer.parseInt(amount);
		} catch (NumberFormatException e) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "INVALID_AMOUNT");
			error.put("message", "amount must be number");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}

		Payment payment = paymentService.selectByOrderId(orderId);
		if (payment == null) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "ORDER_NOT_FOUND");
			error.put("message", "order not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}

		if (payment.getAmount() != amountValue) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "AMOUNT_MISMATCH");
			error.put("message", "amount mismatch");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}

		// TODO: Toss Payments 승인 API 호출
		log.info("confirmPayment payload: paymentKey={}, orderId={}, amount={}", paymentKey, orderId, amount);
		paymentService.markApproved(orderId, paymentKey);

		int periodMonths = resolvePeriodByAmount(amountValue);
		if (periodMonths <= 0) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "INVALID_PERIOD");
			error.put("message", "unsupported amount");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
		subscriptionService.activateSubscription(payment.getUserNo(), periodMonths);

		Map<String, Object> response = new HashMap<>();
		response.put("status", "DONE");
		response.put("paymentKey", paymentKey);
		response.put("orderId", orderId);
		response.put("amount", amountValue);
		return ResponseEntity.ok(response);
	}

	// 결제 웹훅 수신
	@PostMapping("/payments/webhook")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> webhook(@RequestBody Map<String, Object> payload) {
		// TODO: 이벤트 검증 + DB 반영
		log.info("webhook payload: {}", payload);
		Map<String, Object> response = new HashMap<>();
		response.put("result", "OK");
		return ResponseEntity.ok(response);
	}

	// 주문번호/금액 생성
	@PostMapping("/payments/order")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> createOrder(
		@RequestBody Map<String, Object> payload,
		Principal principal
	) {
		if (principal == null) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "UNAUTHORIZED");
			error.put("message", "login required");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

		User user = userService.selectByUserId(principal.getName());
		if (user == null) {
			Map<String, Object> error = new HashMap<>();
			error.put("code", "UNAUTHORIZED");
			error.put("message", "user not found");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

		Integer period = payload.get("period") instanceof Number
			? ((Number) payload.get("period")).intValue()
			: null;

		int amount = resolveAmountByPeriod(period);
		String orderId = generateOrderId();
		String orderName = "AI검색 구독권";

		try {
			paymentService.createOrder(user.getNo(), orderId, orderName, amount);
		} catch (Exception e) {
			log.error("❌ createOrder 실패 - userNo={}, orderId={}, amount={}", user.getNo(), orderId, amount, e);
			Map<String, Object> error = new HashMap<>();
			error.put("code", "ORDER_CREATION_FAILED");
			error.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("orderId", orderId);
		response.put("amount", amount);
		response.put("orderName", orderName);
		return ResponseEntity.ok(response);
	}

	private int resolveAmountByPeriod(Integer period) {
		if (period == null) {
			return 23520;
		}
		switch (period) {
			case 1:
				return 4900;
			case 3:
				return 13200;
			case 6:
				return 23520;
			default:
				return 23520;
		}
	}

	private int resolvePeriodByAmount(int amount) {
		switch (amount) {
			case 4900:
				return 1;
			case 13200:
				return 3;
			case 23520:
				return 6;
			default:
				return 0;
		}
	}

	private String generateOrderId() {
		return "ORDER_" + UUID.randomUUID().toString().replace("-", "");
	}
}
