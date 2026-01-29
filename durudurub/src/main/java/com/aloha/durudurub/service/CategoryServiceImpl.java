package com.aloha.durudurub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<SubCategory> listSubByCategory(int categoryNo) {
        return categoryMapper.listSubByCategory(categoryNo);
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
}