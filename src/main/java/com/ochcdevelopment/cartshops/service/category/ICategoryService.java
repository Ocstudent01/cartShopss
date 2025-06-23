package com.ochcdevelopment.cartshops.service.category;

import com.ochcdevelopment.cartshops.model.Category;

import java.util.List;

public interface ICategoryService {
    //ojo esto para que se vea en la clase CategoryService tengo que entrar en esa clase y agregarle implementation
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);


}
