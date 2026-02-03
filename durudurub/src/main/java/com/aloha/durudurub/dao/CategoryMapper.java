package com.aloha.durudurub.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aloha.durudurub.dto.Category;
import com.aloha.durudurub.dto.SubCategory;

/**
 * 카테고리 매퍼
 */
@Mapper
public interface CategoryMapper {
    
    List<Category> list();
    
    Category selectByNo(@Param("no") int no);
    
    int insert(Category category);
    
    int update(Category category);
    
    int delete(@Param("no") int no);
    
    List<SubCategory> listBySubCategory(@Param("categoryNo") int categoryNo);
    
    SubCategory selectSubByNo(@Param("no") int no);
    
    int insertSub(SubCategory subCategory);
    
    int updateSub(SubCategory subCategory);
    
    int deleteSub(@Param("no") int no);
}