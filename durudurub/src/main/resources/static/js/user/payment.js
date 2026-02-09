// 결제 페이지 JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // 구독 옵션 선택
    const subscriptionOptions = document.querySelectorAll('.subscription-option');
    const paymentAmountEl = document.getElementById('paymentAmount');
    const renewalPeriodEl = document.getElementById('renewalPeriod');
    const btnPayment = document.getElementById('btnPayment');

    subscriptionOptions.forEach(option => {
        option.addEventListener('click', function() {
            // 모든 옵션에서 selected 제거
            subscriptionOptions.forEach(opt => opt.classList.remove('selected'));
            // 선택된 옵션에 selected 추가
            this.classList.add('selected');

            // 가격 및 기간 업데이트
            const period = this.dataset.period;
            const price = this.dataset.price;
            
            paymentAmountEl.textContent = `₩${Number(price).toLocaleString()}`;
            renewalPeriodEl.textContent = `${period}개월`;
        });
    });

    // 카드번호 입력 포맷팅 (4자리마다 공백)
    const cardNumberInput = document.getElementById('cardNumber');
    if (cardNumberInput) {
        cardNumberInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\s/g, '');
            let formattedValue = value.match(/.{1,4}/g)?.join(' ') || value;
            e.target.value = formattedValue;
        });
    }

    // 유효기간 입력 포맷팅 (MM/YY)
    const expiryInput = document.getElementById('expiry');
    if (expiryInput) {
        expiryInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length >= 2) {
                value = value.slice(0, 2) + '/' + value.slice(2, 4);
            }
            e.target.value = value;
        });
    }

    // CVC 숫자만 입력
    const cvcInput = document.getElementById('cvc');
    if (cvcInput) {
        cvcInput.addEventListener('input', function(e) {
            e.target.value = e.target.value.replace(/\D/g, '');
        });
    }

    // 약관 동의 체크 확인
    const termCheckboxes = document.querySelectorAll('.term-checkbox');
    
    function checkAllTerms() {
        const allChecked = Array.from(termCheckboxes).every(checkbox => checkbox.checked);
        btnPayment.disabled = !allChecked;
    }

    termCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', checkAllTerms);
    });

    // 초기 버튼 상태 설정
    checkAllTerms();

    // 결제 버튼 클릭
    btnPayment.addEventListener('click', async function() {
        // 카드 정보 유효성 검사
        const cardNumber = cardNumberInput ? cardNumberInput.value.replace(/\s/g, '') : '';
        const expiry = expiryInput ? expiryInput.value : '';
        const cvc = cvcInput ? cvcInput.value : '';
        const cardHolderInput = document.getElementById('cardHolder');
        const cardHolder = cardHolderInput ? cardHolderInput.value : '';

        if (cardNumberInput) {
            if (!cardNumber || cardNumber.length !== 16) {
                alert('올바른 카드번호를 입력해주세요.');
                cardNumberInput.focus();
                return;
            }

            if (!expiry || expiry.length !== 5) {
                alert('올바른 유효기간을 입력해주세요. (MM/YY)');
                expiryInput.focus();
                return;
            }

            if (!cvc || cvc.length !== 3) {
                alert('올바른 CVC를 입력해주세요.');
                cvcInput.focus();
                return;
            }

            if (!cardHolder) {
                alert('카드 소유자명을 입력해주세요.');
                cardHolderInput.focus();
                return;
            }
        }

        // 선택된 구독 정보
        const selectedOption = document.querySelector('.subscription-option.selected');
        const period = selectedOption.dataset.period;
        const price = selectedOption.dataset.price;

        // 결제 처리 (실제로는 서버로 전송)
        if (confirm(`${period}개월 구독 ₩${Number(price).toLocaleString()}을 결제하시겠습니까?`)) {
            try {
                const headers = {
                    'Content-Type': 'application/json',
                };
                const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');
                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch('/payments/order', {
                    method: 'POST',
                    headers,
                    body: JSON.stringify({ period: Number(period) }),
                });

                if (!response.ok) {
                    throw new Error('주문 생성에 실패했습니다.');
                }

                const order = await response.json();
                const clientKey = window.TOSS_CLIENT_KEY;
                if (!clientKey) {
                    alert('결제 키가 설정되지 않았습니다.');
                    return;
                }

                const customerKey = btoa(Math.random()).slice(0, 20);
                const tossPayments = TossPayments(clientKey);
                const payment = tossPayments.payment({ customerKey });

                await payment.requestPayment({
                    method: 'CARD',
                    amount: {
                        currency: 'KRW',
                        value: Number(order.amount),
                    },
                    orderId: order.orderId,
                    orderName: order.orderName,
                    successUrl: window.location.origin + '/payments/success',
                    failUrl: window.location.origin + '/payments/fail',
                });
            } catch (error) {
                alert(error.message || '주문 생성 중 오류가 발생했습니다.');
            }
        }
    });

    // 입력 필드 포커스 시 플레이스홀더 색상 변경
    const formInputs = document.querySelectorAll('.form-input');
    formInputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.color = '#101828';
        });

        input.addEventListener('blur', function() {
            if (!this.value) {
                this.style.color = 'rgba(10, 10, 10, 0.5)';
            }
        });
    });
});
