 // ⭐ 리더인 모임

        // 날짜 포맷
        fetch(`/users/mypage/club/${clubNo}/members`)
            .then(res => res.json())
            .then(data => {
                console.log(data.pendingList)
            })

        function formatDate(dateStr){
        if(!dateStr) return ''

        const d = new Date(dateStr)
        const yyyy = d.getFullYear()
        const mm = String(d.getMonth()+1).padStart(2,'0')
        const dd = String(d.getDate()).padStart(2,'0')

        return `${yyyy}-${mm}-${dd}`
        }

// 멤버 관리 토글
document.querySelectorAll('.btn-leader-manage').forEach(button => {
    button.addEventListener('click', function() {
        const clubNo = this.getAttribute('data-club-no');
        const managementArea = document.getElementById('memberManagement-' + clubNo);
        const icon = this.querySelector('.material-symbols-outlined:last-child');
        
        if (managementArea.style.display === 'none' || managementArea.style.display === '') {
            managementArea.style.display = 'block';
            icon.textContent = 'expand_less';
        } else {
            managementArea.style.display = 'none';
            icon.textContent = 'expand_more';
        }
    });
});

// 가입요청
function loadPendingMembers(clubNo) {
    $.ajax({
        url: `/users/mypage/club/hostClub/${clubNo}/pending`
    })
}
// 승인된 멤버
