package by.bsuir.service.business;

import by.bsuir.entity.User;

public interface CheckCategoryService {

    boolean existByName(String name, User user);
}
