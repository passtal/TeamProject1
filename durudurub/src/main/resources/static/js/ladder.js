// 사다리 게임 JavaScript

document.addEventListener('DOMContentLoaded', function() {
    const participantCountEl = document.getElementById('participantCount');
    const decreaseBtn = document.getElementById('decreaseBtn');
    const increaseBtn = document.getElementById('increaseBtn');
    const sliderFill = document.getElementById('sliderFill');
    const participantInputs = document.getElementById('participantInputs');
    const prizeInputs = document.getElementById('prizeInputs');
    const generateBtn = document.getElementById('generateBtn');

    let participantCount = 4;
    const minParticipants = 2;
    const maxParticipants = 8;

    // 슬라이더 업데이트
    function updateSlider() {
        const percentage = ((participantCount - minParticipants) / (maxParticipants - minParticipants)) * 100;
        sliderFill.style.width = percentage + '%';
        participantCountEl.textContent = participantCount;
    }

    // 입력 필드 업데이트
    function updateInputs() {
        // 참가자 입력 필드
        participantInputs.innerHTML = '';
        for (let i = 1; i <= participantCount; i++) {
            const input = document.createElement('input');
            input.type = 'text';
            input.className = 'text-input';
            input.placeholder = '참가자' + i;
            input.value = '참가자' + i;
            participantInputs.appendChild(input);
        }

        // 결과/상품 입력 필드
        prizeInputs.innerHTML = '';
        for (let i = 1; i <= participantCount; i++) {
            const input = document.createElement('input');
            input.type = 'text';
            input.className = 'text-input';
            input.placeholder = i + '등';
            input.value = i + '등';
            prizeInputs.appendChild(input);
        }
    }

    // 참가자 수 감소
    decreaseBtn.addEventListener('click', function() {
        if (participantCount > minParticipants) {
            participantCount--;
            updateSlider();
            updateInputs();
        }
    });

    // 참가자 수 증가
    increaseBtn.addEventListener('click', function() {
        if (participantCount < maxParticipants) {
            participantCount++;
            updateSlider();
            updateInputs();
        }
    });

    // 사다리 생성 버튼
    generateBtn.addEventListener('click', function() {
        // 참가자 이름 수집
        const participants = [];
        const participantInputFields = participantInputs.querySelectorAll('input');
        participantInputFields.forEach(input => {
            const name = input.value.trim();
            if (name) {
                participants.push(name);
            }
        });

        // 결과/상품 수집
        const prizes = [];
        const prizeInputFields = prizeInputs.querySelectorAll('input');
        prizeInputFields.forEach(input => {
            const prize = input.value.trim();
            if (prize) {
                prizes.push(prize);
            }
        });

        // 유효성 검사
        if (participants.length !== participantCount) {
            alert('모든 참가자 이름을 입력해주세요.');
            return;
        }

        if (prizes.length !== participantCount) {
            alert('모든 결과/상품을 입력해주세요.');
            return;
        }

        // 사다리 게임 생성 및 표시
        generateLadder(participants, prizes);
    });

    // 사다리 생성 및 표시 함수
    function generateLadder(participants, prizes) {
        const ladderGame = document.getElementById('ladderGame');
        const participantButtons = document.getElementById('participantButtons');
        const prizeDisplay = document.getElementById('prizeDisplay');
        const canvas = document.getElementById('ladderCanvas');
        const ctx = canvas.getContext('2d');
        const resultMessage = document.getElementById('resultMessage');
        const resultList = document.getElementById('resultList');

        // 이전 결과 초기화
        clickedParticipants.clear();
        resultList.innerHTML = '';
        resultMessage.style.display = 'none';

        // 사다리 게임 섹션 먼저 표시
        ladderGame.style.display = 'block';

        // 캔버스 크기 설정 (표시 후에 설정해야 offsetWidth가 정상적으로 계산됨)
        setTimeout(() => {
            canvas.width = canvas.offsetWidth;
            canvas.height = 565;

            // 사다리 데이터 생성
            const ladderData = createLadderData(participants.length);

            // 참가자 버튼 생성
            participantButtons.innerHTML = '';
            participants.forEach((name, index) => {
                const btn = document.createElement('button');
                btn.className = 'participant-btn';
                btn.textContent = name;
                btn.dataset.index = index;
                btn.addEventListener('click', () => animateLadder(index, ladderData, prizes, participants));
                participantButtons.appendChild(btn);
            });

            // 결과/상품 표시
            prizeDisplay.innerHTML = '';
            prizes.forEach(prize => {
                const box = document.createElement('div');
                box.className = 'prize-box';
                box.textContent = prize;
                prizeDisplay.appendChild(box);
            });

            // 사다리 그리기
            drawLadder(ctx, canvas.width, canvas.height, participants.length, ladderData);
            ladderGame.scrollIntoView({ behavior: 'smooth', block: 'center' });
            
            // 모든 결과 보기 버튼 이벤트 등록
            const showAllBtn = document.getElementById('showAllBtn');
            if (showAllBtn) {
                const newShowAllBtn = showAllBtn.cloneNode(true);
                showAllBtn.parentNode.replaceChild(newShowAllBtn, showAllBtn);
                newShowAllBtn.addEventListener('click', () => showAllResults(ladderData, prizes, participants));
            }
        }, 10);
    }

    // 사다리 시뮬레이션 (특정 시작점에서 도착점 계산)
    function simulateLadderPath(startIndex, ladderData, participantCount) {
        let currentCol = startIndex;
        
        ladderData.forEach((row) => {
            if (currentCol > 0 && row[currentCol - 1]) {
                currentCol--;
            }
            else if (currentCol < participantCount - 1 && row[currentCol]) {
                currentCol++;
            }
        });
        
        return currentCol;
    }

    function createLadderData(participantCount) {
        let attempts = 0;
        const maxAttempts = 100;
        
        while (attempts < maxAttempts) {
            attempts++;
            
            const rows = 15;
            const ladderData = [];
            
            for (let rowIndex = 0; rowIndex < rows; rowIndex++) {
                const row = [];
                const isLastRows = rowIndex >= rows - 2;
                let prevHasLine = false;
                
                for (let colIndex = 0; colIndex < participantCount - 1; colIndex++) {
                    if (isLastRows) {
                        row.push(false);
                    } else if (prevHasLine) {
                        row.push(false);
                        prevHasLine = false;
                    } else {
                        const hasLine = Math.random() > 0.4;
                        row.push(hasLine);
                        prevHasLine = hasLine;
                    }
                }
                ladderData.push(row);
            }
            
            const results = [];
            for (let i = 0; i < participantCount; i++) {
                const result = simulateLadderPath(i, ladderData, participantCount);
                results.push(result);
            }
            
            // 중복 검사
            const uniqueResults = new Set(results);
            if (uniqueResults.size === participantCount) {
                console.log(`사다리 생성 성공 (시도 ${attempts}회)`);
                return ladderData;
            }
        }
        
        console.error('사다리 생성 실패, 기본 사다리 반환');
        return Array(15).fill(null).map(() => Array(participantCount - 1).fill(false));
    }

    // 사다리 그리기
    function drawLadder(ctx, width, height, participantCount, ladderData) {
        ctx.clearRect(0, 0, width, height);
        ctx.strokeStyle = '#00a651';
        ctx.lineWidth = 2;

        const padding = 80;
        const usableWidth = width - padding * 2;
        const columnSpacing = usableWidth / (participantCount - 1);
        const rowHeight = height / (ladderData.length + 1);

        for (let i = 0; i < participantCount; i++) {
            const x = padding + i * columnSpacing;
            ctx.beginPath();
            ctx.moveTo(x, 20);
            ctx.lineTo(x, height - 20);
            ctx.stroke();
            
            // 시작점 원 그리기
            ctx.beginPath();
            ctx.arc(x, 20, 5, 0, 2 * Math.PI);
            ctx.fillStyle = '#00a651';
            ctx.fill();
        }

        ladderData.forEach((row, rowIndex) => {
            const y = 20 + (rowIndex + 1) * rowHeight;
            row.forEach((hasLine, colIndex) => {
                if (hasLine) {
                    const x1 = padding + colIndex * columnSpacing;
                    const x2 = padding + (colIndex + 1) * columnSpacing;
                    ctx.beginPath();
                    ctx.moveTo(x1, y);
                    ctx.lineTo(x2, y);
                    ctx.stroke();
                }
            });
        });
    }

    // 사다리 타기 애니메이션 (참가자 배열 교환 방식)
    const participantColors = ['#ff0000', '#0066ff', '#ff6600', '#9900ff', '#00cc66', '#ff00cc', '#ffcc00', '#00ccff'];
    const clickedParticipants = new Set(); // 이미 클릭한 참가자 추적
    let isAutoPlaying = false; // 자동 재생 중인지 체크

    function animateLadder(startIndex, ladderData, prizes, participantNames, isAuto = false) {
        const canvas = document.getElementById('ladderCanvas');
        const ctx = canvas.getContext('2d');
        const participantCount = prizes.length;
        const participantButtons = document.querySelectorAll('.participant-btn');
        const resultMessage = document.getElementById('resultMessage');
        const resultList = document.getElementById('resultList');
        const ladderOverlay = document.getElementById('ladderOverlay');
        
        if (clickedParticipants.size === 0 && !isAuto) {
            canvas.classList.remove('hidden');
            if (ladderOverlay) {
                ladderOverlay.classList.add('hidden');
            }
        }
        
        if (clickedParticipants.has(startIndex)) {
            return;
        }
        
        clickedParticipants.add(startIndex);
        
        participantButtons[startIndex].disabled = true;
        participantButtons[startIndex].classList.add('clicked');
        
        const padding = 80;
        const usableWidth = canvas.width - padding * 2;
        const columnSpacing = usableWidth / (participantCount - 1);
        const rowHeight = canvas.height / (ladderData.length + 1);

        let currentCol = startIndex;
        const path = [{x: padding + currentCol * columnSpacing, y: 20}];

        ladderData.forEach((row, rowIndex) => {
            const y = 20 + (rowIndex + 1) * rowHeight;
            
            if (currentCol > 0 && row[currentCol - 1]) {
                path.push({x: padding + currentCol * columnSpacing, y: y});
                currentCol--;
                path.push({x: padding + currentCol * columnSpacing, y: y});
            }
            else if (currentCol < participantCount - 1 && row[currentCol]) {
                path.push({x: padding + currentCol * columnSpacing, y: y});
                currentCol++;
                path.push({x: padding + currentCol * columnSpacing, y: y});
            }
            else {
                path.push({x: padding + currentCol * columnSpacing, y: y});
            }
        });

        path.push({x: padding + currentCol * columnSpacing, y: canvas.height - 20});

        // 애니메이션
        let step = 0;
        const animationSpeed = 15;
        const pathColor = participantColors[startIndex % participantColors.length];

        const animate = setInterval(() => {
            if (step < path.length - 1) {
                ctx.strokeStyle = pathColor;
                ctx.lineWidth = 3;
                ctx.beginPath();
                ctx.moveTo(path[step].x, path[step].y);
                ctx.lineTo(path[step + 1].x, path[step + 1].y);
                ctx.stroke();
                step++;
            } else {
                clearInterval(animate);
                
                const prizeBoxes = document.querySelectorAll('.prize-box');
                if (!prizeBoxes[currentCol].classList.contains('winner')) {
                    prizeBoxes[currentCol].classList.add('winner');
                }
                
                const participantName = participantNames[startIndex];
                
                // 결과 메시지 추가
                const resultItem = document.createElement('p');
                resultItem.className = 'result-item';
                resultItem.textContent = `🎉 ${participantName}님의 결과는 "${prizes[currentCol]}"입니다!`;
                resultList.appendChild(resultItem);
                
                resultMessage.style.display = 'block';
                
                if (clickedParticipants.size === participantCount) {
                    setTimeout(() => {
                        const finalMessage = document.createElement('p');
                        finalMessage.className = 'result-item';
                        finalMessage.style.marginTop = '16px';
                        finalMessage.style.fontSize = '18px';
                        finalMessage.textContent = '✅ 모든 참가자의 결과가 확인되었습니다!';
                        resultList.appendChild(finalMessage);
                        
                        // 다시하기 버튼 표시
                        const retryBtn = document.getElementById('retryBtn');
                        retryBtn.style.display = 'flex';
                        isAutoPlaying = false;
                        
                        // 모든 결과 보기 버튼 비활성화
                        const showAllBtn = document.getElementById('showAllBtn');
                        if (showAllBtn) {
                            showAllBtn.disabled = true;
                        }
                    }, 300);
                }
            }
        }, animationSpeed);
    }

    // 모든 결과 보기 기능
    function showAllResults(ladderData, prizes, participantNames) {
        if (isAutoPlaying) return;
        
        isAutoPlaying = true;
        const participantCount = participantNames.length;
        const showAllBtn = document.getElementById('showAllBtn');
        const participantButtons = document.querySelectorAll('.participant-btn');
        const canvas = document.getElementById('ladderCanvas');
        const ladderOverlay = document.getElementById('ladderOverlay');
        
        canvas.classList.remove('hidden');
        if (ladderOverlay) {
            ladderOverlay.classList.add('hidden');
        }
        
        showAllBtn.disabled = true;
        participantButtons.forEach(btn => btn.disabled = true);
        
        let currentIndex = 0;
        
        function playNext() {
            if (currentIndex < participantCount) {
                if (clickedParticipants.has(currentIndex)) {
                    currentIndex++;
                    playNext();
                    return;
                }
                
                animateLadder(currentIndex, ladderData, prizes, participantNames, true);
                
                // 시간 딜레이
                setTimeout(() => {
                    currentIndex++;
                    playNext();
                }, 1500);
            }
        }
        
        playNext();
    }

    // 다시하기 버튼 이벤트
    document.addEventListener('click', function(e) {
        if (e.target && e.target.id === 'retryBtn') {
            const ladderGame = document.getElementById('ladderGame');
            const resultMessage = document.getElementById('resultMessage');
            const resultList = document.getElementById('resultList');
            const retryBtn = document.getElementById('retryBtn');
            const canvas = document.getElementById('ladderCanvas');
            const ctx = canvas.getContext('2d');
            const participantButtons = document.querySelectorAll('.participant-btn');
            const prizeBoxes = document.querySelectorAll('.prize-box');
            const ladderOverlay = document.getElementById('ladderOverlay');
            
            // 초기화
            clickedParticipants.clear();
            resultList.innerHTML = '';
            retryBtn.style.display = 'none';
            resultMessage.style.display = 'none';
            isAutoPlaying = false;
            
            // 사다리 가리기
            canvas.classList.add('hidden');
            if (ladderOverlay) {
                ladderOverlay.classList.remove('hidden');
            }
            
            // 참가자 버튼 초기화
            participantButtons.forEach(btn => {
                btn.disabled = false;
                btn.classList.remove('clicked', 'inactive');
            });
            
            // 결과 박스 초기화
            prizeBoxes.forEach(box => {
                box.classList.remove('winner');
            });
            
            // 모든 결과 보기 버튼 초기화
            const showAllBtn = document.getElementById('showAllBtn');
            if (showAllBtn) {
                showAllBtn.disabled = false;
            }
            
            // 초기화
            const participantCount = participantButtons.length;
            const ladderData = createLadderData(participantCount);
            drawLadder(ctx, canvas.width, canvas.height, participantCount, ladderData);
            
            const participants = Array.from(participantButtons).map(btn => btn.textContent);
            const prizes = Array.from(prizeBoxes).map(box => box.textContent);
            
            participantButtons.forEach((btn, index) => {
                const oldBtn = btn.cloneNode(true);
                btn.parentNode.replaceChild(oldBtn, btn);
                oldBtn.addEventListener('click', () => animateLadder(index, ladderData, prizes, participants));
            });
            
            ladderGame.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    });

    // 초기화
    updateSlider();
});
