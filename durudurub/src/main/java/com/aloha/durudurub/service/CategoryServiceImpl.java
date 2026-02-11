package com.aloha.durudurub.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dao.CategoryMapper;
import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.dto.SubCategory;

/**
 * 카테고리 서비스 구현체
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 이미지 추가
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/categories/";
    private static final String DB_URL_PREFIX = "/uploads/categories/";


    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public Category selectByNo(int no) {
        return categoryMapper.selectByNo(no);
    }

    @Override
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public int delete(int no) {
        return categoryMapper.delete(no);
    }

    @Override
    public List<SubCategory> listBySubCategory(int categoryNo) {
        return categoryMapper.listBySubCategory(categoryNo);
    }

    @Override
    public SubCategory selectSubByNo(int no) {
        return categoryMapper.selectSubByNo(no);
    }

    @Override
    public int insertSub(SubCategory subCategory) {
        return categoryMapper.insertSub(subCategory);
    }

    @Override
    public int updateSub(SubCategory subCategory) {
        return categoryMapper.updateSub(subCategory);
    }

    @Override
    public int deleteSub(int no) {
        return categoryMapper.deleteSub(no);
    }

    // 이미지 추가
    // 대분류 등록
    @Override
    @Transactional
    public int insertWithFile(Category category, MultipartFile iconFile) throws Exception {

        // seq 기본값
        if (category.getSeq() < 0) category.setSeq(0);

        // 파일 저장(있으면)
        if (iconFile != null && !iconFile.isEmpty()) {
            String savedUrl = saveCategoryIcon(iconFile);
            category.setIcon(savedUrl); // categories.icon 컬럼에 URL 저장
        }

        // icon 없어도 등록 가능하게 할지 정책 선택:
        // - 배너는 필수였지만, 카테고리는 선택일 가능성이 높아서 필수 체크는 안 걸었음.
        return categoryMapper.insert(category);
    }

    // 대분류 수정
    @Override
    @Transactional
    public int updateWithFile(Category category, MultipartFile iconFile) throws Exception {

        Category old = categoryMapper.selectByNo(category.getNo());

        if (old == null) throw new IllegalArgumentException("카테고리가 존재하지 않습니다: " + category.getNo());

        // 새 파일이 있으면 저장 후 icon 교체
        if (iconFile != null && !iconFile.isEmpty()) {
            String savedUrl = saveCategoryIcon(iconFile);
            category.setIcon(savedUrl);
        } else {
            // 새 파일 없고 icon 값도 비어있으면 기존 유지
            if (!StringUtils.hasText(category.getIcon()) && old != null) {
                category.setIcon(old.getIcon());
            }
        }

        int result = categoryMapper.update(category);

        // 기존 파일 삭제(아이콘 교체된 경우)
        if (result > 0 && iconFile != null && !iconFile.isEmpty() && old != null) {
            if (StringUtils.hasText(old.getIcon()) && !old.getIcon().equals(category.getIcon())) {
                deleteFileIfLocal(old.getIcon());
            }
        }

        return result;
    }

    // 대분류 삭제
    @Override
    @Transactional
    public int deleteWithFile(int no) {
        Category old = categoryMapper.selectByNo(no);
        int result = categoryMapper.delete(no);

        if (result > 0 && old != null) {
            deleteFileIfLocal(old.getIcon());
        }
        return result;
    }

    // 파일 저장
    private String saveCategoryIcon(MultipartFile iconFile) throws IOException {

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String originalName = iconFile.getOriginalFilename();
        String ext = "";

        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf('.'));
        }

        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
        File dest = new File(dir, savedName);
        iconFile.transferTo(dest);

        System.out.println("**************CATEGORY ICON SAVE PATH = " + dest.getAbsolutePath());
        System.out.println("**************URL = " + (DB_URL_PREFIX + savedName));

        return DB_URL_PREFIX + savedName;
    }

    private void deleteFileIfLocal(String imageUrl) {
        if (!StringUtils.hasText(imageUrl)) return;
        if (!imageUrl.startsWith("/uploads/")) return;

        String relativePath = imageUrl.replaceFirst("^/uploads/", "");
        File file = new File(System.getProperty("user.dir") + "/uploads/" + relativePath);

        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("파일 삭제 실패!" + file.getAbsolutePath());
            }
        }
    }
}