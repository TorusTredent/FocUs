package by.bsuir.service.dto;

import by.bsuir.dto.category.SaveCategoryDto;
import by.bsuir.dto.category.UpdateCategoryDto;

public interface EditCategoryService {

    boolean save(SaveCategoryDto saveCategoryDto);

    boolean deleteById(Long categoryId);

    boolean update(UpdateCategoryDto updateCategoryDto);
}
