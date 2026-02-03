/* 두루두루 공통 스크립트 (jQuery Ajax) */
/* TODO: 구현 */

// header
$(document).ready(function() {
    $('.profile-icon').on('click', function() {
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