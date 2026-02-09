// 삭제할 배너 ID를 저장할 변수
        let bannerNo = null;

        
        function deleteBanner(no, title) {
            bannerNo = no;
            document.getElementById('deleteBannerTitle').textContent = title ? `"${title}"` : '';
            document.getElementById('deleteModal').style.display = 'flex';
        }
        
        function closeDeleteModal() {
            document.getElementById('deleteModal').style.display = 'none';
            bannerNo = null;
        }
        
        function confirmDelete() {
            if (bannerNo) {

                // CSRF 토큰 문제 (403)
                const token = $("meta[name='_csrf']").attr("content");
                const header = $("meta[name='_csrf_header']").attr("content");

                $.ajax({
                    url: `/admin/api/banners/${bannerNo}`,
                    method: 'DELETE',
                    beforeSend: function (xhr) {     
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (res) {
                        closeDeleteModal();
                        // 목록 다시 받기 (동기화)
                        getList()
                    },
                    error: function (xhr, status, error) {
                        console.error('*****삭제 실패!')
                        alert('삭제 실패!')
                    }
                })
            }
        }