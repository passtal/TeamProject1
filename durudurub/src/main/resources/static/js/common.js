/* 두루두루 공통 스크립트 (jQuery Ajax) */
/* TODO: 구현 */

// header
// user or admin 에 따라 드롭다운 달라짐
$(document).ready(function() {
    // 로그인 후 사람 아이콘
    $('.icon-container').on('click', function(e) {
        // 원인 : 클릭 이벤트 버블링
        // 아이콘 눌러도, document click 실행 -> 바로 닫힘
        // e.stopPropagation(); -> 이 이벤트를 부모로 전달하지 마!
        e.stopPropagation();

        const userDropdown = $('#user-dropdown')
        const adminDropdown = $('#admin-dropdown')
        
        // .length : 선택된 요소의 개수
        if (userDropdown.length) {
            // .toggle() : 보임/숨김 상태를 반전시키는 함수
            // 보이지 않는 상태 -> display: block (보이게)
            // 보이는 상태 -> display : none (숨기게)
            $('#user-dropdown').toggle();
            $('#admin-dropdown').hide();
        } else if (adminDropdown.length) {
            $('#admin-dropdown').toggle();
            $('#user-dropdown').hide();
        }
    })

    $(document).on('click', function() {
        $('#user-dropdown').hide();
        $('#admin-dropdown').hide();
    })
})
/* 이벤트 버블링
     : 이벤트가 위로 올라간다!
   icon-container 클릭 시, body 클릭, document 클릭 되버림 */
/* .length
     : jQuery 선택자는 항상 "객체" 반환
     -> 찾아서도 객체, 못 찾아도 객체
     -> 항상 true 가 되므로, .length로 체크!
   0 이라면 의미 없음 / 1 이라면 의미 있음! */