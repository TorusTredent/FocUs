package by.bsuir.service.dto.impl;

import by.bsuir.entity.Category;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.dto.GetCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetCategoryServiceImpl implements GetCategoryService {

    @Override
    public List<Category> getCategoriesByNames(User user, List<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> user.getCategory().stream()
                        .filter(category -> category.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(String.format("category with name %s not found", name), NOT_FOUND)))
                .toList();
    }
}
