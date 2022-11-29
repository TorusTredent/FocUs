package by.bsuir.service.dto.impl;

import by.bsuir.dto.category.SaveCategoryDto;
import by.bsuir.dto.category.UpdateCategoryDto;
import by.bsuir.entity.Category;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.service.business.CheckCategoryService;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.EditCategoryService;
import by.bsuir.service.entity.CategoryService;
import by.bsuir.service.entity.UserService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static by.bsuir.entity.enums.category.CATEGORY_TYPE.CUSTOM;
import static by.bsuir.exception.enums.ERROR_CODE.CATEGORY_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class EditCategoryServiceImpl implements EditCategoryService {

    private final CategoryService categoryService;
    private final SecurityService securityService;
    private final CheckCategoryService checkCategoryService;
    private final UserService userService;

    public EditCategoryServiceImpl(CategoryService categoryService, SecurityService securityService, CheckCategoryService checkCategoryService, UserService userService) {
        this.categoryService = categoryService;
        this.securityService = securityService;
        this.checkCategoryService = checkCategoryService;
        this.userService = userService;
    }

    @Override
    public boolean save(SaveCategoryDto saveCategoryDto) {
        User user = userService.findByFirebaseId(getUid());
        if (checkCategoryService.existByName(saveCategoryDto.getName(), user)) {
            throw new BusinessException(String.format("Category with name %s is already exist", saveCategoryDto.getName()));
        }
        Category category = categoryService.save(Category.builder()
                .name(saveCategoryDto.getName())
                .textColor(saveCategoryDto.getTextColor())
                .textBackgroundColor(saveCategoryDto.getTextBackgroundColor())
                .category_type(CUSTOM)
                .build());
        user.getCategory().add(category);
        userService.save(user);
        return false;
    }

    @Override
    @Modifying
    @Transactional
    public boolean deleteById(Long categoryId) {
        User user = userService.findByFirebaseId(getUid());
        Category category = categoryService.findById(categoryId);

        if (!userService.existsByUserIdAndCategory(user.getId(), category)) {
            throw new BusinessException(String.format("Category with id %s not found", categoryId), CATEGORY_NOT_FOUND, NOT_FOUND);
        }

        user.getCategory().remove(category);
        categoryService.delete(category);
        return true;
    }

    @Override
    @Transactional
    @Modifying
    public boolean update(UpdateCategoryDto updateCategoryDto) {
        User user = userService.findByFirebaseId(getUid());
        Category category = categoryService.findById(updateCategoryDto.getId());

        if (!userService.existsByUserIdAndCategory(user.getId(), category)) {
            throw new BusinessException(String.format("Category with id %s not found", updateCategoryDto.getId()),
                                                                                        CATEGORY_NOT_FOUND, NOT_FOUND);
        }

        category.setName(updateCategoryDto.getName());
        category.setTextColor(updateCategoryDto.getTextColor());
        category.setTextBackgroundColor(updateCategoryDto.getTextBackgroundColor());

        return true;
    }


    private String getUid() {
        return securityService.getUser().getUid();
    }
}
