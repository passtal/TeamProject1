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

// 검색 기능
// 검색 debounce - 깜빡임 처리
function debounce(fn, delay) {
    let timer
    return function (...args) {
        clearTimeout(timer)
        timer = setTimeout(() => fn.apply(this, args), delay)
    }
}

// 카드 필터 
function filterClubsByKeyword(term) {
  const keyword = (term || "").toLowerCase().trim();

  // 모임 리스트 페이지가 아니면 무시
  const grid = document.getElementById("club-grid");
  if (!grid) return;

  // data-category-no 있는 카드 컬럼
  const cols = grid.querySelectorAll('[data-category-no]');

  let visibleCount = 0;

  cols.forEach(col => {
    const title = col.querySelector(".card-title")?.textContent?.toLowerCase() ?? "";
    const category = col.querySelector(".badge.bg-light")?.textContent?.toLowerCase() ?? "";
    const desc = col.querySelector(".card-text")?.textContent?.toLowerCase() ?? "";
    const location = col.querySelector(".bi-geo-alt")?.parentElement?.textContent?.toLowerCase() ?? "";

    const matched =
      !keyword ||
      title.includes(keyword) ||
      category.includes(keyword) ||
      desc.includes(keyword) ||
      location.includes(keyword);

    col.style.display = matched ? "" : "none";
    if (matched) visibleCount++;
  });

  // 개수
  const clubCountEl = document.getElementById("club-count");
  if (clubCountEl) clubCountEl.textContent = visibleCount;

  // 빈 화면
  const emptyState = document.getElementById("empty-state");
  if (emptyState) emptyState.style.display = visibleCount === 0 ? "block" : "none";
}
$(document).on('input.clubSearch', '#clubSearch', function () {
  console.log("typing:", this.value);
});

// 검색 - 이벤트
$(document).on('input', '#clubSearch', debounce(function(e){
    filterClubsByKeyword(e.target.value.toLowerCase())
}, 200))
// 엔터 후 페이지 이동(GET) 막기
$(document).on("submit", ".search-container form", function (e) {
  if (document.getElementById("club-grid")) e.preventDefault();
});


/* 이벤트 버블링
     : 이벤트가 위로 올라간다!
   icon-container 클릭 시, body 클릭, document 클릭 되버림 */
/* .length
     : jQuery 선택자는 항상 "객체" 반환
     -> 찾아서도 객체, 못 찾아도 객체
     -> 항상 true 가 되므로, .length로 체크!
   0 이라면 의미 없음 / 1 이라면 의미 있음! */