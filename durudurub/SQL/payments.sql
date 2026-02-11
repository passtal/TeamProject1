-- 22. payments (결제 내역 - 토스페이먼츠)
-- 토스페이먼츠 API 연동시에 더 추가할 사항 있으면 추가해주세요.
-- payment_key : 토스에서 발행하는 결제 고유키
-- order_id : 우리가 생성하는 고유 주문번호 (이걸로 결제 검증)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `payments`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `payments` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `subscription_no` INT NULL COMMENT 'FK',
    `payment_key` VARCHAR(200) NULL COMMENT '토스 결제키',
    `order_id` VARCHAR(100) NOT NULL COMMENT '고유 주문번호',
    `order_name` VARCHAR(100) NOT NULL COMMENT 'AI검색 구독권',
    `amount` INT NOT NULL COMMENT '결제금액 (원)',
    `currency` VARCHAR(10) NOT NULL DEFAULT 'KRW' COMMENT '통화',
    `type` VARCHAR(20) NOT NULL DEFAULT 'NORMAL' COMMENT '결제 타입',
    `status` ENUM('READY', 'DONE', 'CANCELED', 'FAILED') DEFAULT 'READY' COMMENT '결제 상태',
    `requested_at` TIMESTAMP NULL COMMENT '결제 요청 일시',
    `approved_at` TIMESTAMP NULL COMMENT '결제 승인 일시',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`subscription_no`) REFERENCES `subscriptions`(`no`) ON DELETE SET NULL,
    UNIQUE KEY uk_payment_order (`order_id`),
    INDEX idx_payment_key (`payment_key`)
);