package by.bsuir.service.dto.impl;

import by.bsuir.entity.Category;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetCategoryService;
import by.bsuir.service.entity.CategoryService;
import by.bsuir.service.entity.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetCategoryServiceImpl implements GetCategoryService {

    private final SecurityService securityService;
    private final UserService userService;
    private final CategoryService categoryService;

    public GetCategoryServiceImpl(SecurityService securityService, UserService userService, CategoryService categoryService) {
        this.securityService = securityService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Category> getCategoriesByNames(User user, List<String> categoryNames) {
        return categoryNames.stream()
                .map(name -> user.getCategory().stream()
                        .filter(category -> category.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(String.format("category with name %s not found", name), NOT_FOUND)))
                .toList();
    }

    @Override
    public List<Category> getAllCategories() {
        User user = userService.findByFirebaseId(getUid());
        List<Category> categories = user.getCategory();
        if (categories == null || categories.isEmpty()) {
            throw new BusinessException("User dont have categories", NOT_FOUND);
        }

        return categories;
    }


    private String getUid() {
        return securityService.getUser().getUid();
    }
}
