package by.bsuir.service.business.impl;

import by.bsuir.entity.Category;
import by.bsuir.entity.User;
import by.bsuir.service.business.CheckCategoryService;
import org.springframework.stereotype.Service;

@Service
public class CheckCategoryServiceImpl implements CheckCategoryService {

    @Override
    public boolean existByName(String name, User user) {
        return user.getCategory().stream()
                .anyMatch(category -> category.getName().equals(name));
    }
}
