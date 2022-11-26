package by.bsuir.service.entity;

import by.bsuir.entity.Category;

public interface CategoryService {

    Category save(Category category);

    Category findById(Long categoryId);

    void delete(Category category);
}
