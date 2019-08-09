package com.java.springboot.service;

import com.java.springboot.entity.Category;
import com.java.springboot.entity.User;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    int insert(Category category);
    void delete(int id);
    Category findById(int id);
    int update(Category category);
    User login(User user);
}
