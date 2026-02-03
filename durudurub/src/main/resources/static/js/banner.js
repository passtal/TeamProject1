// URL 수정 필수!
// 배너 클릭 시 조회 수 증가
// 테스트 전, 페이지마다 콘솔 넣고 오류 찾기

let no = document.getElementById("no").value // 전역 변수

// 모달창 열기
function openModal() {
    $("#open-modal").show()
}
// 모달창 닫기
function closeModal() {
    $("#close-modal").hide()
}
//-------------------------------------------------------------
// 목록
function bannerList() {
    $.ajax({
        url: `/admin/adminpage/banner/list`,
        method: 'GET',
        dataType: 'json',
        success: function (bannerList) {
            if (bannerList.length == 0) {
                alert ("조회된 배너가 없습니다.")
            } else {
                $('#banner-list').empty()

                let tr = ''

                for(const banner of bannerList) {
                    tr += `<tr> 
                            <td>${banner.title}</td>
                            <td>${banner.imageUrl}</td>
                            <td>${banner.linkUrl}</td>
                            <td>${banner}</td>
                            <td>${banner.isActive}</td>
                            <td>${banner.seq}</td>
                        </tr>`
                }
                $('#banner-list').html(tr)
            }
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
}

// 조회
function bannerSelect() {
    $.ajax({
        url: `/admin/adminpage/banner/modal/${no}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            if (!data) {
                alert ("조회된 배너가 없습니다.")
            } else {
                $("#banner-title").val(data.title)
                $("#banner-imageUrl").val(data.imageUrl)
                $("#banner-linkUrl").val(data.linkUrl)
                $("#banner-description").val(data.description)
                $("#banner-isActive").prop('checked', data.isActive)
                $("#banner-position").val(data.position)
                $("#banner-startDate").val(data.startDate)
                $("#banner-endDate").val(data.endDate)
                $("#banner-seq").val(data.seq)
                $("#banner-clickCount").val(data.clickCount)
            }
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
}

// 등록
function bannerCreate() {
    let title = $("#banner-title").val()
    let imageUrl = $("#banner-imageUrl").val()
    let linkUrl = $("#banner-linkUrl").val()
    let description = $("#banner-description").val()
    // input + checkbox -> .prop
    let isActive = $("#banner-isActive").prop('checked')
    let position = $("#banner-position").val()
    let startDate = $("#banner-startDate").val()
    let endDate = $("#banner-endDate").val()
    let seq = $("#banner-seq").val()

    let data = {
        'title' : title,
        'imageUrl' : imageUrl,
        'linkUrl' : linkUrl,
        'description' : description,
        'isActive' : isActive,
        'position' : position,
        'startDate' : startDate,
        'endDate' : endDate,
        'seq' : seq
    }

    $.ajax({
        url: `http://127.0.0.1:8080/admin/adminpage/banner/modal`,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result, status, xhr) {
            alert("배너가 추가되었습니다")
            closeModal()
            getList()
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
}

// 수정
function bannerUpdate() {

    let title = $("#banner-title").val()
    let imageUrl = $("#banner-imageUrl").val()
    let linkUrl = $("#banner-linkUrl").val()
    let description = $("#banner-description").val()
    // input + checkbox -> .prop
    let isActive = $("#banner-isActive").prop('checked')
    let position = $("#banner-position").val()
    let startDate = $("#banner-startDate").val()
    let endDate = $("#banner-endDate").val()
    let seq = $("#banner-seq").val()

    let data = {
        'title' : title,
        'imageUrl' : imageUrl,
        'linkUrl' : linkUrl,
        'description' : description,
        'isActive' : isActive,
        'position' : position,
        'startDate' : startDate,
        'endDate' : endDate,
        'seq' : seq
    }

    $.ajax({
        url: `http://127.0.0.1:8080/admin/adminpage/banner/modal`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result, status, xhr) {
            alert("배너가 수정되었습니다")
            closeModal()
            getList()
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
}

// 삭제
function bannerDelete() {
    $.ajax({
        url: `http://127.0.0.1:8080/admin/adminpage/banner/modal/${no}`,
        method: 'DELETE',
        success: function (result, status, xhr) {
            alert("배너가 삭제되었습니다")
            closeModal()
            getList()
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
}