package by.bsuir.service.entity.impl;

import by.bsuir.entity.Category;
import by.bsuir.exception.BusinessException;
import by.bsuir.repository.CategoryRepository;
import by.bsuir.service.entity.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static by.bsuir.exception.enums.ERROR_CODE.CATEGORY_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(String.format("Category with id %s not found", categoryId), CATEGORY_NOT_FOUND, NOT_FOUND);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
