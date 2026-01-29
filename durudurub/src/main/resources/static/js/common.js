/* 두루두루 공통 스크립트 (jQuery Ajax) */
/* TODO: 구현 */

// header
/* 로고 클릭 : index.html */
function index(home) {
    // 메인 url 주소 : /index
    location.href = '/index'
}

/* 네비게이션 메뉴 */
/* 모임 둘러보기 */
function list(params) {
    location.href = '/index/list'
}

/* 공지사항 */
function notice(params) {
    location.href = '/index/notice'
}

/* 미니게임 */
function minigame(params) {
    location.href = '/index/minigame'
}

/* 로그인 전후 구분
/  - 구조 : HTML(틀) -> onclick="login()" -> AJAX로 로그인 여부 조회 -> 렌더링 함수(show/hide) */
//        1. onclick="login()"
let userNo = null;

function login() {
    let url = `http://127.0.0.1:8080/api/index/login`;

    $.ajax({
        url: url,  // 임시url
        method: 'GET',
        dataType: 'json',
        success: function(res) {
            // res : response 줄임말
            if (res.loginId) {
                userNo = res.id
                // loginId : 서버에서 넘겨받은 'json' key : loginId
                renderLoggedIn(res);
            } else {
                renderLoggedOut();
            }
        },
        error: function() {
            console.error("로그인 정보 조회 실패!");
            renderLoggedOut();
        }
    });
}
//        2. 렌더링 함수 - 로그인O
function renderLoggedIn(data) {
    $("#beforeLogin").hide();
    $("#afterLogin").show();

    if (data.userName === 'user') {
        $("#admin").hide();
        $("#club").show();
    } else {
        $("#admin").show();
        $("#club").hide();
    }
}
//        3. 렌더링 함수 - 로그인X
function renderLoggedOut() {
    $("#beforeLogin").show();
    $("#afterLogin").hide();
}
//        4. 페이지 로드
$(document).ready(function () {
    $.ajax({
        url: "/",   //임시 url
        method: "GET",
        dataType: "json",
        success: function (res) {
            if (res.loginId) {
                userNo = res.id
                renderLoggedIn(res);
            } else {
                renderLoggedOut();
            }
        }
    })
})
// function signup() {
//     location.href = '/index/signup'
// }

/* 로그인 후 */
/* 로그인 모임 만들기 */
function create() {
    location.href = '/index/user/create?id=' + userNo
}
/* 프로필 아이콘 : 드롭다운 */
function userDropdown() {
    location.href = '/index/user/sideMenu?id=' + userNo
}
/* 마이페이지 */
function userpage() {
    location.href = '/index/user/mypage?id=' + userNo
}
/* 나의 모임 */
function userclub() {
    location.href = '/index/user/myclub?id=' + userNo
}
/* 관리자 */
function adminpage() {
    location.href = '/index/admin/mypage'
}
/* 로그아웃 */
function logout() {
    // 컨트롤러 무효화 or
    location.href = '/index'
    // AJAX 무효화
    $.ajax({
        url: "/",
        method: "POST",
        success: function() {
            userNo = null;
            renderLoggedOut();
            location.href = "/index";
        },
        error: function () {
            console.log("로그아웃 실패!");
        }
    });
}
