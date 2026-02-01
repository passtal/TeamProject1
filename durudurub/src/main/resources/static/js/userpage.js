// mypage - index

// 회원 정보 조회
function userSelect() {
    // 마이페이지 index 바로 뜸
    // html 에서 구현!
}
// -----------------------------------------------------------------
// 회원 정보 수정 (수정버튼)
function updateBtn() {
    $("#view-mode").hide() // 조회 화면 -> span
    $("#edit-mode").show() // 수정 화면 -> text

    $("#username-input").val($("#username-span").text())
    $("#userage-input").val($("#userage-span").text())
    $("#usergender-input").val($("#usergender-span").text())
    $("#useraddress-input").val($("#useraddress-span").text())

    // 사진 미리보기 기존 값 설정
    $("#userprofile-img").attr("src", $("#userprofile-img").attr("src"));
}

// 회원 정보 수정 (취소버튼)
function cancelBtn() {
    $("#edit-mode").hide() // 수정 화면 -> text
    $("#view-mode").show() // 조회 화면 -> span
}

// 회원 정보 수정 (저장버튼)
function saveBtn() {

    // 사진 추가 시 FormData 필요!
    const formData = new FormData();

    formData.append("username", $("#username-input").val());
    formData.append("age", parseInt($("#userage-input").val()));
    formData.append("gender", $("#usergender-input").val());
    formData.append("address", $("#useraddress-input").val());

    // 사진이 선택된 경우에만 추가
    const imgFile = $("#profile-file")[0].files[0];
    if (imgFile) {
        formData.append("profileImg", imgFile);
    }

    $.ajax({
        url: `http://127.0.0.1:8080/api/user/mypage`,
        method: 'PUT',
        // processData: false → 데이터를 문자열로 변환하지 않고 원본 그대로 보냄
        processData: false,
        // contentType: false → 헤더를 자동으로 설정하지 말고, 브라우저가 알아서 content-type 붙게 비워두기
        contentType: false,
        // data: formData → 여기에 실제로 보낼 데이터 (formData 객체) 들어있음
        data: formData,
        success: function () {
            alert("회원 정보가 수정되었습니다")

            // 화면 업데이트
            $("#userprofile-img").attr("src", $("#userprofile-img").attr("src"));
            $("#username-span").text($("#username-input").val());
            $("#userage-span").text($("#userage-input").val());
            $("#usergender-span").text($("#usergender-input").val());
            $("#useraddress-span").text($("#useraddress-input").val());

            cancelBtn()
        }, 
        error: function (xhr, status, error) {
            // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
            // status : AJAX 요청 상태를 문자열로 나타낸 것
            if (xhr.status === 404) {
                alert("잘못된 요청입니다");
            } else {
                alert("서버 내부 오류입니다.");
            }
        }
    })
}
// ------------------------------------------------------------------
// 회원 탈퇴 - 모달 열기 (회원탈퇴 버튼)
function openModel() {
    $("#delete-model").show()
    // $("#delete-modal").css("display", "flex")
}
// 회원 탈퇴 - 모달 닫기 
function closeModel() {
    $("#delete-model").hide()
}
// 회원 탈퇴 (탈퇴하기 버튼)
function userDelete() {
    $.ajax({
        url: `http://127.0.0.1:8080/api/user/mypage/modal`,
        method: 'DELETE',
        success: function (result, status, xhr) {
            alert("탈퇴가 완료되었습니다")
            window.location.href = "/"; // 로그아웃 후 메인 이동
        }, 
        error: function (xhr, status, error) {
            // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
            // status : AJAX 요청 상태를 문자열로 나타낸 것
            if (xhr.status === 404) {
                alert("잘못된 요청입니다");
            } else if (xhr.status === 403) {
                alert("관리자 권한이 없습니다");
            } else {
                alert("서버 내부 오류입니다.")
            }
        }
    })
    closeModal(); // 모달 닫기
}
    


