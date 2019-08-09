package com.java.springboot.mapper;

import java.util.List;

import com.java.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.java.springboot.entity.Category;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper {

    List<Category> findAll();

    int insert(Category category);

    void delete(int id);

    Category findById(int id);

    int update(Category category);

    User login(User user);
}