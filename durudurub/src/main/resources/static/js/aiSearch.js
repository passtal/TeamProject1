$(document).ready(function() {

    // AI 검색 버튼 클릭 시 → 상태 확인 후 적절한 모달 표시
    $(document).on('click', '.ai-search-btn', function(e) {
        e.preventDefault();
        e.stopPropagation();

        // 서버에 로그인/구독/잔여횟수 상태 확인 요청
        $.ajax({
            url: '/api/ai/status',
            type: 'GET',
            success: function(res) {
                if (!res.loggedIn) {
                    // 비로그인 → 로그인 안내 팝업
                    $('#aiLoginModal').modal('show');
                } else if (!res.canSearch) {
                    // 로그인했지만 무료 3회 소진 + 미구독 → 구독 안내 팝업
                    $('#aiSubscribeModal').modal('show');
                } else {
                    // 검색 가능 → AI 검색 모달
                    if (res.remaining !== null && res.remaining >= 0) {
                        $('#aiSearchRemaining').text('남은 무료 횟수: ' + res.remaining + '회')
                            .removeClass('bg-purple').addClass('bg-secondary');
                    } else {
                        $('#aiSearchRemaining').text('프리미엄')
                            .removeClass('bg-secondary').addClass('bg-purple');
                    }
                    $('#aiSearchModal').modal('show');
                }
            },
            error: function() {
                // 상태 확인 실패 시 로그인 팝업 표시
                $('#aiLoginModal').modal('show');
            }
        });
    });

    // AI 검색 실행
    $('#aiSearchForm').on('submit', function(e) {
        e.preventDefault();

        var message = $('#aiSearchInput').val().trim();
        if (!message) {
            alert('검색어를 입력해주세요!');
            return;
        }

        // 로딩 표시
        $('#aiSearchResult').html(
            '<div class="text-center py-4">' +
            '  <div class="spinner-border text-primary"></div>' +
            '  <p class="mt-2">AI가 모임을 찾고 있어요...</p>' +
            '</div>'
        );

        // AJAX POST 요청
        $.ajax({
            url: '/api/ai/search',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ message: message }),
            success: function(res) {
                displayResult(res);
            },
            error: function(xhr) {
                if (xhr.status === 403) {
                    // 검색 도중 횟수 소진 → 모달 닫고 구독 팝업
                    $('#aiSearchModal').modal('hide');
                    setTimeout(function() {
                        $('#aiSubscribeModal').modal('show');
                    }, 300);
                } else {
                    $('#aiSearchResult').html(
                        '<div class="alert alert-danger">' +
                        '  AI 검색 중 오류가 발생했습니다. 다시 시도해주세요.' +
                        '</div>'
                    );
                }
            }
        });
    });

    // 결과 화면 표시
    function displayResult(res) {
        var html = '';

        // AI 추천 메시지 (줄바꿈 처리)
        var aiMsg = res.aiMessage ? res.aiMessage.replace(/\n/g, '<br>') : '';
        html += '<div class="alert alert-info mb-3" style="white-space: pre-line;">';
        html += '  <i class="bi bi-robot"></i> ' + aiMsg;
        html += '</div>';

        // 남은 횟수 업데이트
        if (res.remaining !== null && res.remaining !== undefined && res.remaining >= 0) {
            $('#aiSearchRemaining').text('남은 무료 횟수: ' + res.remaining + '회')
                .removeClass('bg-purple').addClass('bg-secondary');
        } else if (res.remaining === -1) {
            $('#aiSearchRemaining').text('프리미엄')
                .removeClass('bg-secondary').addClass('bg-purple');
        }

        // 모임 목록
        if (res.clubs && res.clubs.length > 0) {
            html += '<p class="text-muted small mb-2">🔍 추천 모임 ' + res.clubs.length + '건</p>';
            html += '<div class="list-group">';
            res.clubs.forEach(function(club) {
                html += '<a href="/club/' + club.no + '" class="list-group-item list-group-item-action">';
                html += '  <div class="d-flex align-items-center">';
                var imgSrc = (club.thumbnailImg && club.thumbnailImg !== 'null') ? club.thumbnailImg : '/img/hero-image.png';
                html += '    <img src="' + imgSrc + '" ';
                html += '         class="rounded me-3" style="width:60px;height:60px;object-fit:cover;"';
                html += '         onerror="this.src=\'/img/hero-image.png\'">';
                html += '    <div>';
                html += '      <h6 class="mb-1">' + club.title + '</h6>';
                html += '      <p class="mb-0 text-muted small">';
                if (club.category && club.category.name) {
                    html += '<span class="badge bg-light text-dark me-1">' + club.category.name + '</span>';
                }
                html += (club.location || '') + '</p>';
                html += '    </div>';
                html += '  </div>';
                html += '</a>';
            });
            html += '</div>';
        }

        // 정확한 매칭이 아닌 경우 → 모임 만들기 버튼 표시
        if (!res.exactMatch) {
            html += '<div class="text-center mt-3 pt-3" style="border-top: 1px solid #eee;">';
            html += '  <p class="text-muted small mb-2">원하는 모임을 직접 만들어볼 수도 있어요!</p>';
            html += '  <a href="/club/create" class="btn btn-outline-primary">';
            html += '    <i class="bi bi-plus-circle"></i> 모임 만들기';
            html += '  </a>';
            html += '</div>';
        }

        $('#aiSearchResult').html(html);
    }
});
