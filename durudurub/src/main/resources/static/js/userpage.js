// 사용자 리스트
function getList() {
    $.ajax({
        url: `/admin/fragment/users`,
        method: 'GET',
        dataType: 'json',
        success: function (userList) {
            if (userList.length == 0) {
                alert('조회된 사용자가 없습니다.')
            } else {
                // 기존 목록 제거
                $('#user-body').empty()
                // 새로운 서버데이터 요청 후 받은 데이터 기반으로 재생성
                let tr = ''
                for(const user of userList) {
                    tr += `<tr>
                            <td>${user.username}</td> //닉네임, 이메일, 가입일, 구독 상태, 삭제
                            <td>${user.userId}</td>
                            <td>${user.createdAt}</td>
                            <td>${user.username}</td>
                        `
                }
            }
        }
    })
}





// 사용자 상세 정보 표시
function showUserDetail(username, email, joinDate, userNo) {
    document.getElementById('detailUsername').textContent = username;
    document.getElementById('detailUserId').textContent = email;
    
    // 가입일 포맷 변경 (2024. 1. 15. → 2024년 1월 15일)
    const date = new Date(joinDate)

    const yyyy = date.getFullYear()
    const mm = String(date.getMonth() + 1).padStart(2, '0')
    const dd = String(date.getDate()).padStart(2, '0')

    const hh = date.getHours() //0~23
    const mi = String(date.getMinutes()).padStart(2, '0')

    const ampm = hh < 12 ? '오전' : '오후'
    const hours = hh % 12 || 12     // 오전 12시(0->12)/오후 12시(12->12)/오후 1시(13->1)

    document.getElementById('detailCreatedAt').textContent
        = `${yyyy}년 ${mm}월 ${dd}일 ${ampm} ${String(hours).padStart(2,'0')}:${mi}`

    // 구독 상태 배지
    // const badgeContainer = document.getElementById('detailBadge');
    // let badgeClass = '';
    // let badgeText = '';
    
    // if (status === 'admin') {
    //     badgeClass = 'badge badge-admin';
    //     badgeText = '관리자';
    // } else if (status === 'subscribed') {
    //     badgeClass = 'badge badge-subscribed';
    //     badgeText = '구독 중';
    // } else {
    //     badgeClass = 'badge badge-unsubscribed';
    //     badgeText = '미구독';
    // }
    
    // badgeContainer.innerHTML = `<span class="${badgeClass}">${badgeText}</span>`;
    
    // 신고 횟수
    // document.getElementById('detailReportCount').textContent = `${reportCount}회`;
    
    // 사용자 ID
    document.getElementById('detailUserNo').textContent = userNo
    
    // 모달 표시
    document.getElementById('userDetailModal').style.display = 'flex';
}
