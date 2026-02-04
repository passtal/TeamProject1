// 테스트 전, 페이지마다 콘솔 넣고 오류 찾기
// ⭐⭐⭐⭐ URL 변경 필수!!!
// mypage - index

// 회원 정보 조회
function userSelect() {
    // 마이페이지 index 바로 뜸
    // html 에서 구현!
}
// -----------------------------------------------------------------
// 회원 정보 수정 (수정버튼)
function updateBtn() {
    $("#view-mode, #view-content").hide() // 조회 화면 -> span
    $("#edit-mode, #edit-content").show() // 수정 화면 -> text

    // .trim() : 공백 입력 방지!
    //          option '여자' -> span ' 여자 ' : 적용X
    $("#username-input").val($("#username-span").text().trim())
    $("#useraddress-input").val($("#useraddress-span").text().trim())
    // 나이 : "세" 추가하기
    // "" : "세" -> "" (숫자만 뽑기)
    const ageText = $("#userage-span").text().replace("세", "").trim()
    $("#userage-input").val(ageText);
    // 성별 : 선택 안함 추가하기
    const genderText = $("#usergender-span").text().trim()
    $("#usergender-select").val(genderText === "선택 안함" ? "" : genderText)

    // 사진 미리보기 기존 값 설정
    $("#profileImageEdit").attr("src", $("#profileImageView").attr("src"));
}

// 회원 정보 수정 (취소버튼)
function cancelBtn() {
    $("#edit-mode, #edit-content").hide() // 수정 화면 -> text
    $("#view-mode, #view-content").show() // 조회 화면 -> span
}

// 회원 정보 수정 (저장버튼)
function saveBtn() {

    // 사진 추가 시 FormData 필요!
    const formData = new FormData();

    formData.append("username", $("#username-input").val());
    // "세" 추가하기
    const ageVal = $("#userage-input").val()
    formData.append("age", ageVal ? parseInt(ageVal, 10) : 0);
    
    formData.append("gender", $("#usergender-select").val());
    formData.append("address", $("#useraddress-input").val());

    // 사진이 선택된 경우에만 추가
    const imgFile = $("#profile-file")[0].files[0];
    if (imgFile) {
        formData.append("profileImg", imgFile);
    }

    $.ajax({
        url: `/users/mypage`,
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
            // $("#userprofile-img").attr("src", $("#userprofile-img").attr("src"));
            $("#username-span").text($("#username-input").val());
            // 나이 : "세" 붙이기
            const newAge = $("#userage-input").val();
            $("#userage-span").text(newAge ? `${newAge}세` : "예: 25세");
            // 성별 : 선택 안할 시 "선택 안함" 기본!
            const newGender = $("#usergender-select").val();
            $("#usergender-span").text(newGender ? newGender : "선택 안함");
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
function openModal() {
    // $("#delete-modal").show()
    $("#delete-modal").css("display", "flex")
}
// 회원 탈퇴 - 모달 닫기 
function closeModal() {
    $("#delete-modal").hide()
}
// 회원 탈퇴 (탈퇴하기 버튼)
function userDelete() {
    $.ajax({
        url: `/users/mypage/modal`,
        method: 'DELETE',
        success: function (result, status, xhr) {
            alert("탈퇴가 완료되었습니다")
            window.location.href = "/"; // 탈퇴 후 메인 이동
        }, 
        error: function (xhr, status, error) {
            // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
            // status : AJAX 요청 상태를 문자열로 나타낸 것
            if (xhr.status === 404) {
                alert("잘못된 요청입니다");
            } else {
                alert("서버 내부 오류입니다.")
            }
        }
    })
}
// -----------------------------------------------------------------
// 내모임 관리

// 상위 카테고리 (참여중 / 리더 / 신청 중) - type 이용해봄
function myclubList(type) {
    $.ajax({
        url: `/users/mypage/club/list`,
        method: 'GET',
        data: { type: type}, // 버튼 : myclubList('APPROVED') / myclubList('HOST') / myclubList('PENDING')
        dataType: 'html', // HTML(fragment) 문자열
        success: function (html) {
            // 리스트 영역 교체
            // #myclubList : 해당 영역 안에 서버에서 받아온 HTML 통째로 넣기
            // 버튼마다 변경되는 리스트 fragment 필요! (HTML 구현)
            $("#myclubList").html(html);
        },
        error: function (xhr, status, error) {
            // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
            // status : AJAX 요청 상태를 문자열로 나타낸 것
            if (xhr.status === 404) {
                alert("잘못된 요청입니다");
            } else {
                alert("서버 내부 오류입니다.")
            }
        }
    })
}

// 상위 카테고리 (개수) - countApproved / countHost / countPending
function countList(type, diff) {
    // 타입에 따라 바뀌는 객체 매핑
    const map = {
        APPROVED: "#countApproved",
        PENDING: "#countPending",
        HOST: "#countHost"
    }
    // 선택된 타입을 제이쿼리 객체로 담기
    let changeType = $(map[type])
    // 숫자로 반환
    let count = Number(changeType.text())
    // 반환된 숫자와 받은 파라미터 계산 (0 최소)
    changeType.text(Math.max(0, count + diff))
}

// 모임 탈퇴 - 모달 열기 (회원탈퇴 버튼)
function clubOpenModel() {
    $("#leave-model").show()
    // $("#leave-model").css("display", "flex")
}
// 모임 탈퇴 - 모달 닫기 
function clubCloseModel() {
    $("#leave-model").hide()
}

// // 탈퇴하기 버튼
// $(document).on("click", ".btn-leave", function () {
//     // this 필요!
//     const $btn = $(this);
//     const clubNo = $btn.data("clubno");

//     $.ajax({
//         url: `http://127.0.0.1:8080/api/user/mypage/club/${clubNo}`,
//         method: 'DELETE',
//         success: function (result, status, xhr) {
//             alert("탈퇴가 완료되었습니다")
//             // 가입된 모임리스트 수 감소
//             countList("APPROVED", -1)
//             // 탈퇴한 모임리스트 카드 삭제
//             btn.closest(".club-card").remove();
//             // 탈퇴 후 /user/mypage/club 으로 이동
//             window.location.href = "/user/mypage/club"; 
//         }, 
//         error: function (xhr, status, error) {
//             // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
//             // status : AJAX 요청 상태를 문자열로 나타낸 것
//             if (xhr.status === 404) {
//                 alert("잘못된 요청입니다");
//             } else {
//                 alert("서버 내부 오류입니다.")
//             }
//         }
//     })
//     clubCloseModel(); // 모달 닫기
// })

// // 신청취소 버튼
// $(document).on("click", ".btn-joincancel", function () {

//     const $btn = $(this);
//     const clubNo = $btn.data("clubno");

//     $.ajax({
//         url: `http://127.0.0.1:8080/api/user/mypage/club/${clubNo}`,
//         method: 'DELETE',
//         success: function (result, status, xhr) {
//             alert("모임 가입 신청이 취소되었습니다")
//             // 신청한 모임리스트 수 감소
//             countList("PENDING", -1)
//             // 신청한 모임리스트 카드 삭제
//             $(this).closest(".club-card").remove();
//             // 탈퇴 후 /user/mypage/club 으로 이동
//             window.location.href = "/user/mypage/club"; 
//         }, 
//         error: function (xhr, status, error) {
//             // xhr : AJAX 요청에 대한 전체 HTTP 응답 객체
//             // status : AJAX 요청 상태를 문자열로 나타낸 것
//             if (xhr.status === 404) {
//                 alert("잘못된 요청입니다");
//             } else {
//                 alert("서버 내부 오류입니다.")
//             }
//         }
//     })
//     clubOpenModel(); // 모달 닫기
// })