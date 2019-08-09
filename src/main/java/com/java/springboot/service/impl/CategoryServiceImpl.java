package com.java.springboot.service.impl;

import com.java.springboot.entity.Category;
import com.java.springboot.entity.User;
import com.java.springboot.mapper.CategoryMapper;
import com.java.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service//让spring创建CategoryServiceImpl的实例，之后注入到CategoryController的categoryService中
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    public List<Category> findAll(){
        return categoryMapper.findAll();
    }
    public int insert(Category category){
        return categoryMapper.insert(category);
    }
    public void delete(int id){
        categoryMapper.delete(id);
    }
    public int update(Category category){
        return categoryMapper.update(category);
    }
    public Category findById(int id){
        return categoryMapper.findById(id);
    }
    public User login(User user){
        return categoryMapper.login(user);
    }
}
