-- 22. payments (결제 내역 - 토스페이먼츠)
-- 토스페이먼츠 API 연동시에 더 추가할 사항 있으면 추가해주세요.
-- payment_key : 토스에서 발행하는 결제 고유키
-- order_id : 우리가 생성하는 고유 주문번호 (이걸로 결제 검증)

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `banners`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `payments` (
    `no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_no` INT NOT NULL COMMENT 'FK',
    `subscription_no` INT NOT NULL COMMENT 'FK',
    `payment_key` VARCHAR(200) NULL COMMENT '토스페이먼츠 결제키',
    `order_id` VARCHAR(100) NOT NULL COMMENT '고유 주문번호',
    `order_name` VARCHAR(100) NOT NULL COMMENT 'AI검색 구독권',
    `amount` INT NOT NULL COMMENT '결제금액 (원)',
    `method` VARCHAR(50) NULL COMMENT '결제수단 (카드/간편결제 등)',
    `status` ENUM('READY', 'DONE', 'CANCELED', 'FAILED') DEFAULT 'READY' COMMENT '결제 상태',
    `approved_at` TIMESTAMP NULL COMMENT '결제 승인 일시',
    `canceled_at` TIMESTAMP NULL COMMENT '취소 일시',
    `cancel_reason` VARCHAR(200) NULL COMMENT '취소 사유',
    `receipt_url` VARCHAR(500) NULL COMMENT '영수증 URL',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`no`),
    FOREIGN KEY (`user_no`) REFERENCES `users`(`no`) ON DELETE CASCADE,
    FOREIGN KEY (`subscription_no`) REFERENCES `subscriptions`(`no`) ON DELETE SET NULL,
    UNIQUE KEY uk_payment_order (`order_id`),
    INDEX idx_payment_key (`payment_key`)
);