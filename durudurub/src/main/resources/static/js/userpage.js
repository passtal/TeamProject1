// 테스트 전, 페이지마다 콘솔 넣고 오류 찾기
// ⭐⭐⭐⭐ URL 변경 필수!!!
// mypage - index



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