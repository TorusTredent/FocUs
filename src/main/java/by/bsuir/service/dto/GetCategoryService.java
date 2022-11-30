package by.bsuir.service.dto;

import by.bsuir.entity.Category;
import by.bsuir.entity.User;

import java.util.List;

public interface GetCategoryService {


    List<Category> getCategoriesByNames(User user, List<String> categoryNames);
    List<Category> getAllCategories();
}
