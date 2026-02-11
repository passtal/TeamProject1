package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.dto.SubCategory;

/**
 * 카테고리 서비스 인터페이스
 */
public interface CategoryService {
    
    List<Category> list();
    
    Category selectByNo(int no);
    
    int insert(Category category);
    
    int update(Category category);

    // 이미지 추가
    int insertWithFile(Category category, MultipartFile iconFile) throws Exception;
    int updateWithFile(Category category, MultipartFile iconFile) throws Exception;
    int deleteWithFile(int no);
        
    int delete(int no);
    
    List<SubCategory> listBySubCategory(int categoryNo);
    
    SubCategory selectSubByNo(int no);
    
    int insertSub(SubCategory subCategory);
    
    int updateSub(SubCategory subCategory);
    
    int deleteSub(int no);

}